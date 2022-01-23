package com.homepanel.astro.service;

import com.homepanel.astro.astro.calculation.MoonBuilder;
import com.homepanel.astro.astro.calculation.SunBuilder;
import com.homepanel.astro.astro.model.Moon;
import com.homepanel.astro.astro.model.Sun;

import java.util.Calendar;

public class Astro {

    private Service service;
    private Sun sun;
    private Moon moon;
    private Long sunCacheExpireTimeInMilliseconds;
    private Long moonCacheExpireTimeInMilliseconds;

    private Service getService() {
        return service;
    }

    private void setService(Service service) {
        this.service = service;
    }

    private Sun getSun() {
        return sun;
    }

    private void setSun(Sun sun) {
        this.sun = sun;
    }

    private Moon getMoon() {
        return moon;
    }

    private void setMoon(Moon moon) {
        this.moon = moon;
    }

    private Long getSunCacheExpireTimeInMilliseconds() {
        return sunCacheExpireTimeInMilliseconds;
    }

    private void setSunCacheExpireTimeInMilliseconds(Long sunCacheExpireTimeInMilliseconds) {
        this.sunCacheExpireTimeInMilliseconds = sunCacheExpireTimeInMilliseconds;
    }

    private Long getMoonCacheExpireTimeInMilliseconds() {
        return moonCacheExpireTimeInMilliseconds;
    }

    private void setMoonCacheExpireTimeInMilliseconds(Long moonCacheExpireTimeInMilliseconds) {
        this.moonCacheExpireTimeInMilliseconds = moonCacheExpireTimeInMilliseconds;
    }

    public Astro(Service service) {
        setService(service);
    }

    public synchronized Sun getSun(long timeInMilliseconds, long refreshIntervalInMilliseconds) {

        if (getSun() == null || getSunCacheExpireTimeInMilliseconds() == null || getSunCacheExpireTimeInMilliseconds() < timeInMilliseconds - refreshIntervalInMilliseconds) {
            setSun(new SunBuilder().createSun(Calendar.getInstance(), getService().getConfig().getAstro().getLatitude(), getService().getConfig().getAstro().getLongitude(), getService().getConfig().getAstro().getAltitude()));
            setSunCacheExpireTimeInMilliseconds(timeInMilliseconds);
        }

        return getSun();
    }

    public synchronized Moon getMoon(long timeInMilliseconds, long refreshIntervalInMilliseconds) {

        if (getMoon() == null || getMoonCacheExpireTimeInMilliseconds() == null || getMoonCacheExpireTimeInMilliseconds() < timeInMilliseconds - refreshIntervalInMilliseconds) {
            setMoon(new MoonBuilder().createMoon(Calendar.getInstance(), getService().getConfig().getAstro().getLatitude(), getService().getConfig().getAstro().getLongitude()));
            setMoonCacheExpireTimeInMilliseconds(timeInMilliseconds);
        }

        return getMoon();
    }
}
