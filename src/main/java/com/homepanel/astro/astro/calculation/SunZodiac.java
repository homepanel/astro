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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.homepanel.astro.astro.model.ZodiacSign;
import com.homepanel.astro.astro.util.DateTimeUtils;

/**
 * Calculates the sign and range of the current zodiac.
 *
 * @author Gerhard Riegler - Initial contribution
 */
public class SunZodiac {
    private Map<Integer, List<com.homepanel.astro.astro.model.SunZodiac>> zodiacsByYear = new HashMap<Integer, List<com.homepanel.astro.astro.model.SunZodiac>>();

    /**
     * Returns the zodiac for the specified calendar.
     */
    public com.homepanel.astro.astro.model.SunZodiac getZodiac(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        List<com.homepanel.astro.astro.model.SunZodiac> zodiacs = zodiacsByYear.get(year);
        if (zodiacs == null) {
            zodiacs = calculateZodiacs(year);
            zodiacsByYear.clear();
            zodiacsByYear.put(year, zodiacs);
        }

        for (com.homepanel.astro.astro.model.SunZodiac zodiac : zodiacs) {
            if (zodiac.isValid(calendar)) {
                return zodiac;
            }
        }

        return null;
    }

    /**
     * Calculates the zodiacs for the current year.
     */
    private List<com.homepanel.astro.astro.model.SunZodiac> calculateZodiacs(int year) {
        List<com.homepanel.astro.astro.model.SunZodiac> zodiacs = new ArrayList<com.homepanel.astro.astro.model.SunZodiac>();

        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.ARIES,
                DateTimeUtils.getRange(year, Calendar.MARCH, 21, year, Calendar.APRIL, 19)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.TAURUS,
                DateTimeUtils.getRange(year, Calendar.APRIL, 20, year, Calendar.MAY, 20)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.GEMINI,
                DateTimeUtils.getRange(year, Calendar.MAY, 21, year, Calendar.JUNE, 20)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.CANCER,
                DateTimeUtils.getRange(year, Calendar.JUNE, 21, year, Calendar.JULY, 22)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.LEO,
                DateTimeUtils.getRange(year, Calendar.JULY, 23, year, Calendar.AUGUST, 22)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.VIRGO,
                DateTimeUtils.getRange(year, Calendar.AUGUST, 23, year, Calendar.SEPTEMBER, 22)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.LIBRA,
                DateTimeUtils.getRange(year, Calendar.SEPTEMBER, 23, year, Calendar.OCTOBER, 22)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.SCORPIO,
                DateTimeUtils.getRange(year, Calendar.OCTOBER, 23, year, Calendar.NOVEMBER, 21)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.SAGITTARIUS,
                DateTimeUtils.getRange(year, Calendar.NOVEMBER, 22, year, Calendar.DECEMBER, 21)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.CAPRICORN,
                DateTimeUtils.getRange(year, Calendar.DECEMBER, 22, year + 1, Calendar.JANUARY, 19)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.CAPRICORN,
                DateTimeUtils.getRange(year - 1, Calendar.DECEMBER, 22, year, Calendar.JANUARY, 19)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.AQUARIUS,
                DateTimeUtils.getRange(year, Calendar.JANUARY, 20, year, Calendar.FEBRUARY, 18)));
        zodiacs.add(new com.homepanel.astro.astro.model.SunZodiac(ZodiacSign.PISCES,
                DateTimeUtils.getRange(year, Calendar.FEBRUARY, 19, year, Calendar.MARCH, 20)));

        return zodiacs;
    }
}