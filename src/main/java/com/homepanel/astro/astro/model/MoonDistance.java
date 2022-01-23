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
 * Holds a distance informations.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 */
public class MoonDistance {

    private Calendar date;
    private Double distance;

    private Calendar getCalendarDate() {
        return date;
    }

    public LocalDateTime getDate() {

        if (getCalendarDate() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarDate().toInstant(), ZoneId.systemDefault());
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("date", DateTimeUtils.getDate(date))
                .append("distance", distance).toString();
    }
}