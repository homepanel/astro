/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.homepanel.astro.astro.calculation;

import com.homepanel.astro.astro.model.*;
import com.homepanel.astro.astro.util.DateTimeUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Map.Entry;

/**
 * Calculates the SunPosition (azimuth, elevation) and Sun data.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 */
public class SunBuilder {
    private static final double J2000 = 2451545.0;
    private static final double SC = 1367; // Solar constant in W/m²
    public static final double DEG2RAD = Math.PI / 180;
    public static final double RAD2DEG = 180. / Math.PI;

    private static final double M0 = 357.5291 * DEG2RAD;
    private static final double M1 = 0.98560028 * DEG2RAD;
    private static final double J0 = 0.0009;
    private static final double J1 = 0.0053;
    private static final double J2 = -0.0069;
    private static final double C1 = 1.9148 * DEG2RAD;
    private static final double C2 = 0.0200 * DEG2RAD;
    private static final double C3 = 0.0003 * DEG2RAD;
    private static final double P = 102.9372 * DEG2RAD;
    private static final double E = 23.45 * DEG2RAD;
    private static final double TH0 = 280.1600 * DEG2RAD;
    private static final double TH1 = 360.9856235 * DEG2RAD;
    private static final double SUN_ANGLE = -0.83;
    private static final double SUN_DIAMETER = 0.53 * DEG2RAD; // sun diameter
    private static final double H0 = SUN_ANGLE * DEG2RAD;
    private static final double H1 = -6.0 * DEG2RAD; // nautical twilight angle
    private static final double H2 = -12.0 * DEG2RAD; // astronomical twilight
                                                      // angle
    private static final double H3 = -18.0 * DEG2RAD; // darkness angle
    private static final double MINUTES_PER_DAY = 60 * 24;
    private static final int CURVE_TIME_INTERVAL = 20; // 20 minutes
    private static final double JD_ONE_MINUTE_FRACTION = 1.0 / 60 / 24;

    /**
     * Calculates the sun position (azimuth and elevation).
     */
    public void setPositionalInfo(Calendar calendar, double latitude, double longitude, Double altitude, Sun sun) {
        double lw = -longitude * DEG2RAD;
        double phi = latitude * DEG2RAD;

        double j = DateTimeUtils.dateToJulianDate(calendar);
        double m = getSolarMeanAnomaly(j);
        double c = getEquationOfCenter(m);
        double lsun = getEclipticLongitude(m, c);
        double d = getSunDeclination(lsun);
        double a = getRightAscension(lsun);
        double th = getSiderealTime(j, lw);

        double azimuth = getAzimuth(th, a, phi, d) / DEG2RAD;
        double elevation = getElevation(th, a, phi, d) / DEG2RAD;
        double shadeLength = getShadeLength(elevation);

        Position position = sun.getPosition();
        position.setAzimuth(azimuth + 180);
        position.setElevation(elevation);
        position.setShadeLength(shadeLength);

        setRadiationInfo(calendar, elevation, altitude, sun);
    }

    /**
     * Calculates sun radiation data.
     */
    public void setRadiationInfo(Calendar calendar, double elevation, Double altitude, Sun sun) {
        double sinAlpha = Math.sin(DEG2RAD * elevation);

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        // Direct Solar Radiation (in W/m²) at the atmosphere entry
        // At sunrise/sunset - calculations limits are reached
        double rOut = (elevation > 3) ? SC * (0.034 * Math.cos(DEG2RAD * (360 * dayOfYear / daysInYear)) + 1) : 0;
        double altitudeRatio = (altitude != null) ? 1 / Math.pow((1 - (6.5 / 288) * (altitude / 1000.0)), 5.256) : 1;
        double m = (Math.sqrt(1229 + Math.pow(614 * sinAlpha, 2)) - 614 * sinAlpha) * altitudeRatio;

        // Direct radiation after atmospheric layer
        // 0.6 = Coefficient de transmissivité
        double rDir = rOut * Math.pow(0.6, m) * sinAlpha;

        // Diffuse Radiation
        double rDiff = rOut * (0.271 - 0.294 * Math.pow(0.6, m)) * sinAlpha;
        double rTot = rDir + rDiff;

        Radiation radiation = sun.getRadiation();
        radiation.setDirect(rDir);
        radiation.setDiffuse(rDiff);
        radiation.setTotal(rTot);
    }

    /**
     * Returns true, if the sun is up all day (no rise and set).
     */
    private boolean isSunUpAllDay(Calendar calendar, double latitude, double longitude, Double altitude) {
        Calendar cal = DateTimeUtils.truncateToMidnight(calendar);
        Sun sun = new Sun();
        for (int minutes = 0; minutes <= MINUTES_PER_DAY; minutes += CURVE_TIME_INTERVAL) {
            setPositionalInfo(cal, latitude, longitude, altitude, sun);
            if (sun.getPosition().getElevation() < SUN_ANGLE) {
                return false;
            }
            cal.add(Calendar.MINUTE, CURVE_TIME_INTERVAL);
        }
        return true;
    }

    /**
     * Calculates all sun rise and sets at the specified coordinates.
     */
    public Sun createSun(Calendar calendar, Double latitude, Double longitude, Double altitude) {
        return createSun(calendar, latitude, longitude, altitude, false);
    }

    private Sun createSun(Calendar calendar, Double latitude, Double longitude, Double altitude, boolean onlyAstro) {
        double lw = -longitude * DEG2RAD;
        double phi = latitude * DEG2RAD;
        double j = DateTimeUtils.midnightDateToJulianDate(calendar) + 0.5;
        double n = getJulianCycle(j, lw);
        double js = getApproxSolarTransit(0, lw, n);
        double m = getSolarMeanAnomaly(js);
        double c = getEquationOfCenter(m);
        double lsun = getEclipticLongitude(m, c);
        double d = getSunDeclination(lsun);
        double jtransit = getSolarTransit(js, m, lsun);
        double w0 = getHourAngle(H0, phi, d);
        double w1 = getHourAngle(H0 + SUN_DIAMETER, phi, d);
        double jset = getSunsetJulianDate(w0, m, lsun, lw, n);
        double jsetstart = getSunsetJulianDate(w1, m, lsun, lw, n);
        double jrise = getSunriseJulianDate(jtransit, jset);
        double jriseend = getSunriseJulianDate(jtransit, jsetstart);
        double w2 = getHourAngle(H1, phi, d);
        double jnau = getSunsetJulianDate(w2, m, lsun, lw, n);
        double jciv2 = getSunriseJulianDate(jtransit, jnau);

        double w3 = getHourAngle(H2, phi, d);
        double w4 = getHourAngle(H3, phi, d);
        double jastro = getSunsetJulianDate(w3, m, lsun, lw, n);
        double jdark = getSunsetJulianDate(w4, m, lsun, lw, n);
        double jnau2 = getSunriseJulianDate(jtransit, jastro);
        double jastro2 = getSunriseJulianDate(jtransit, jdark);

        Sun sun = new Sun();
        sun.setAstroDawn(new Range(DateTimeUtils.toCalendar(jastro2), DateTimeUtils.toCalendar(jnau2)));
        sun.setAstroDusk(new Range(DateTimeUtils.toCalendar(jastro), DateTimeUtils.toCalendar(jdark)));

        if (onlyAstro) {
            return sun;
        }

        sun.setNoon(new Range(DateTimeUtils.toCalendar(jtransit),
                DateTimeUtils.toCalendar(jtransit + JD_ONE_MINUTE_FRACTION)));
        sun.setRise(new Range(DateTimeUtils.toCalendar(jrise), DateTimeUtils.toCalendar(jriseend)));
        sun.setSet(new Range(DateTimeUtils.toCalendar(jsetstart), DateTimeUtils.toCalendar(jset)));

        sun.setCivilDawn(new Range(DateTimeUtils.toCalendar(jciv2), DateTimeUtils.toCalendar(jrise)));
        sun.setCivilDusk(new Range(DateTimeUtils.toCalendar(jset), DateTimeUtils.toCalendar(jnau)));

        sun.setNauticDawn(new Range(DateTimeUtils.toCalendar(jnau2), DateTimeUtils.toCalendar(jciv2)));
        sun.setNauticDusk(new Range(DateTimeUtils.toCalendar(jnau), DateTimeUtils.toCalendar(jastro)));

        boolean isSunUpAllDay = isSunUpAllDay(calendar, latitude, longitude, altitude);

        // daylight
        Range daylightRange = new Range();
        if (sun.getRise().getCalendarStart() == null && sun.getRise().getCalendarEnd() == null) {
            if (isSunUpAllDay) {
                daylightRange = new Range(DateTimeUtils.truncateToMidnight(calendar),
                        DateTimeUtils.truncateToMidnight(addDays(calendar, 1)));
            }
        } else {
            daylightRange = new Range(sun.getRise().getCalendarEnd(), sun.getSet().getCalendarStart());
        }
        sun.setDaylight(daylightRange);

        // morning night
        Sun sunYesterday = createSun(addDays(calendar, -1), latitude, longitude, altitude, true);
        Range morningNightRange = null;
        if (sunYesterday.getAstroDusk().getCalendarEnd() != null
                && DateUtils.isSameDay(sunYesterday.getAstroDusk().getCalendarEnd(), calendar)) {
            morningNightRange = new Range(sunYesterday.getAstroDusk().getCalendarEnd(), sun.getAstroDawn().getCalendarStart());
        } else if (isSunUpAllDay || sun.getAstroDawn().getCalendarStart() == null) {
            morningNightRange = new Range();
        } else {
            morningNightRange = new Range(DateTimeUtils.truncateToMidnight(calendar), sun.getAstroDawn().getCalendarStart());
        }
        sun.setMorningNight(morningNightRange);

        // evening night
        Range eveningNightRange = null;
        if (sun.getAstroDusk().getCalendarEnd() != null && DateUtils.isSameDay(sun.getAstroDusk().getCalendarEnd(), calendar)) {
            eveningNightRange = new Range(sun.getAstroDusk().getCalendarEnd(),
                    DateTimeUtils.truncateToMidnight(addDays(calendar, 1)));
        } else {
            eveningNightRange = new Range();
        }
        sun.setEveningNight(eveningNightRange);

        // night
        if (isSunUpAllDay) {
            sun.setNight(new Range());
        } else {
            Sun sunTomorrow = createSun(addDays(calendar, 1), latitude, longitude, altitude, true);
            sun.setNight(new Range(sun.getAstroDusk().getCalendarEnd(), sunTomorrow.getAstroDawn().getCalendarStart()));
        }

        // eclipse
        SunEclipse eclipse = sun.getEclipse();
        MoonBuilder mc = new MoonBuilder();

        double partial = mc.getEclipse(calendar, MoonBuilder.ECLIPSE_TYPE_SUN, j, MoonBuilder.ECLIPSE_MODE_PARTIAL);
        eclipse.setPartial(DateTimeUtils.toCalendar(partial));
        double ring = mc.getEclipse(calendar, MoonBuilder.ECLIPSE_TYPE_SUN, j, MoonBuilder.ECLIPSE_MODE_RING);
        eclipse.setRing(DateTimeUtils.toCalendar(ring));
        double total = mc.getEclipse(calendar, MoonBuilder.ECLIPSE_TYPE_SUN, j, MoonBuilder.ECLIPSE_MODE_TOTAL);
        eclipse.setTotal(DateTimeUtils.toCalendar(total));

        com.homepanel.astro.astro.calculation.SunZodiac zodiacCalc = new SunZodiac();
        sun.setZodiac(zodiacCalc.getZodiac(calendar));

        Season season = new Season();
        sun.setSeason(season.getSeason(calendar, latitude));

        // phase
        for (Entry<SunPhaseName, Range> rangeEntry : sun.getAllRanges().entrySet()) {
            SunPhaseName entryPhase = rangeEntry.getKey();
            if (rangeEntry.getValue().matches(Calendar.getInstance())) {
                if (entryPhase == SunPhaseName.MORNING_NIGHT || entryPhase == SunPhaseName.EVENING_NIGHT) {
                    sun.getPhase().setName(SunPhaseName.NIGHT);
                } else {
                    sun.getPhase().setName(entryPhase);
                }
            }
        }

        return sun;
    }

    /**
     * Adds the specified days to the calendar.
     */
    private Calendar addDays(Calendar calendar, int days) {
        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal;
    }

    // all the following methods are translated to java based on the javascript
    // calculations of http://www.suncalc.net
    private double getJulianCycle(double j, double lw) {
        return Math.round(j - J2000 - J0 - lw / (2 * Math.PI));
    }

    private double getApproxSolarTransit(double ht, double lw, double n) {
        return J2000 + J0 + (ht + lw) / (2 * Math.PI) + n;
    }

    private double getSolarMeanAnomaly(double js) {
        return M0 + M1 * (js - J2000);
    }

    private double getEquationOfCenter(double m) {
        return C1 * Math.sin(m) + C2 * Math.sin(2 * m) + C3 * Math.sin(3 * m);
    }

    private double getEclipticLongitude(double m, double c) {
        return m + P + c + Math.PI;
    }

    private double getSolarTransit(double js, double m, double lsun) {
        return js + (J1 * Math.sin(m)) + (J2 * Math.sin(2 * lsun));
    }

    private double getSunDeclination(double lsun) {
        return Math.asin(Math.sin(lsun) * Math.sin(E));
    }

    private double getRightAscension(double lsun) {
        return Math.atan2(Math.sin(lsun) * Math.cos(E), Math.cos(lsun));
    }

    private double getSiderealTime(double j, double lw) {
        return TH0 + TH1 * (j - J2000) - lw;
    }

    private double getAzimuth(double th, double a, double phi, double d) {
        double h = th - a;
        return Math.atan2(Math.sin(h), Math.cos(h) * Math.sin(phi) - Math.tan(d) * Math.cos(phi));
    }

    private double getElevation(double th, double a, double phi, double d) {
        return Math.asin(Math.sin(phi) * Math.sin(d) + Math.cos(phi) * Math.cos(d) * Math.cos(th - a));
    }

    private double getShadeLength(double elevation) {
        return 1 / Math.tan(elevation * DEG2RAD);
    }

    private double getHourAngle(double h, double phi, double d) {
        return Math.acos((Math.sin(h) - Math.sin(phi) * Math.sin(d)) / (Math.cos(phi) * Math.cos(d)));
    }

    private double getSunsetJulianDate(double w0, double m, double Lsun, double lw, double n) {
        return getSolarTransit(getApproxSolarTransit(w0, lw, n), m, Lsun);
    }

    private double getSunriseJulianDate(double jtransit, double jset) {
        return jtransit - (jset - jtransit);
    }
}