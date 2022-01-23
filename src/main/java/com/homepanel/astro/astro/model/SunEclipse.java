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
 * Extends the eclipse object with the ring-like eclipse information.
 *
 * @author Gerhard Riegler - Initial contribution
 */
public class SunEclipse extends Eclipse {

    private Calendar ring;

    public Calendar getCalendarRing() {
        return ring;
    }

    public LocalDateTime getRing() {

        if (getCalendarRing() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarRing().toInstant(), ZoneId.systemDefault());
    }

    public void setRing(Calendar ring) {
        this.ring = ring;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("total", DateTimeUtils.getDate(getCalendarTotal()))
                .append("partial", DateTimeUtils.getDate(getCalendarPartial())).append("ring", DateTimeUtils.getDate(ring))
                .toString();
    }
}