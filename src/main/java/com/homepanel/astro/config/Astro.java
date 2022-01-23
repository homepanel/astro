package com.homepanel.astro.config;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "onewire")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Astro {

    private Double longitude;
    private Double latitude;
    private Double altitude;
    private String cronExpression;

    public Double getLongitude() {
        return longitude;
    }

    private void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    private void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    private void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    private void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}