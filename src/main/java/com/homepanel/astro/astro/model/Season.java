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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Holds the season dates of the year and the current name.
 *
 * @author Gerhard Riegler - Initial contribution
 */
public class Season {

    private Calendar spring;
    private Calendar summer;
    private Calendar autumn;
    private Calendar winter;

    private SeasonName name;

    public Calendar getCalendarSpring() {
        return spring;
    }

    public LocalDateTime getSpring() {

        if (getCalendarSpring() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarSpring().toInstant(), ZoneId.systemDefault());
    }

    public void setSpring(Calendar spring) {
        this.spring = spring;
    }

    public Calendar getCalendarSummer() {
        return summer;
    }

    public LocalDateTime getSummer() {

        if (getCalendarSummer() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarSummer().toInstant(), ZoneId.systemDefault());
    }

    public void setSummer(Calendar summer) {
        this.summer = summer;
    }

    public Calendar getCalendarAutumn() {
        return autumn;
    }

    public LocalDateTime getAutumn() {

        if (getCalendarAutumn() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarAutumn().toInstant(), ZoneId.systemDefault());
    }

    public void setAutumn(Calendar autumn) {
        this.autumn = autumn;
    }

    public Calendar getCalendarWinter() {
        return winter;
    }

    public LocalDateTime getWinter() {

        if (getCalendarWinter() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarWinter().toInstant(), ZoneId.systemDefault());
    }

    public void setWinter(Calendar winter) {
        this.winter = winter;
    }

    public SeasonName getName() {
        return name;
    }

    public void setName(SeasonName name) {
        this.name = name;
    }

    public Calendar getNextSeason() {
        return DateTimeUtils.getNext(spring, summer, autumn, winter);
    }
}