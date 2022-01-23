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
package com.homepanel.astro.astro.model;

import com.homepanel.astro.astro.util.DateTimeUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Holds the calculates moon phase informations.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 */
public class MoonPhase {
    private Calendar firstQuarter;
    private Calendar full;
    private Calendar thirdQuarter;
    private Calendar _new;
    private Integer age;
    private Double illumination;
    private Double agePercent;
    private Double ageDegree;

    private MoonPhaseName name;

    public Calendar getCalendarFirstQuarter() {
        return firstQuarter;
    }

    public LocalDateTime getFirstQuarter() {

        if (getCalendarFirstQuarter() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarFirstQuarter().toInstant(), ZoneId.systemDefault());
    }

    public void setFirstQuarter(Calendar firstQuarter) {
        this.firstQuarter = firstQuarter;
    }

    public Calendar getCalendarFull() {
        return full;
    }

    public LocalDateTime getFull() {

        if (getCalendarFull() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarFull().toInstant(), ZoneId.systemDefault());
    }

    public void setFull(Calendar full) {
        this.full = full;
    }

    public Calendar getCalendarThirdQuarter() {
        return thirdQuarter;
    }

    public LocalDateTime getThirdQuarter() {

        if (getCalendarThirdQuarter() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarThirdQuarter().toInstant(), ZoneId.systemDefault());
    }

    public void setThirdQuarter(Calendar thirdQuarter) {
        this.thirdQuarter = thirdQuarter;
    }

    public Calendar getCalendarNew() {
        return _new;
    }

    public LocalDateTime getNew() {

        if (getCalendarNew() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarNew().toInstant(), ZoneId.systemDefault());
    }

    public void setNew(Calendar _new) {
        this._new = _new;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getIllumination() {
        return illumination;
    }

    public void setIllumination(Double illumination) {
        this.illumination = illumination;
    }

    public MoonPhaseName getName() {
        return name;
    }

    public void setName(MoonPhaseName name) {
        this.name = name;
    }

    public Double getAgeDegree() {
        return ageDegree;
    }

    public void setAgeDegree(Double ageDegree) {
        this.ageDegree = ageDegree;
    }

    public Double getAgePercent() {
        return agePercent;
    }

    public void setAgePercent(Double agePercent) {
        this.agePercent = agePercent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("firstQuarter", DateTimeUtils.getDate(firstQuarter)).append("full", DateTimeUtils.getDate(full))
                .append("thirdQuarter", DateTimeUtils.getDate(thirdQuarter)).append("new", DateTimeUtils.getDate(_new))
                .append("age", age).append("ageDegree", ageDegree).append("agePercent", agePercent)
                .append("illumination", illumination).append("name", name).toString();
    }
}
