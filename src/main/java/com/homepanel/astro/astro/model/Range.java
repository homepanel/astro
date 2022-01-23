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
 * Range class which holds a start and a end calendar object.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 */
public class Range {

    private Calendar start;
    private Calendar end;

    public Range() {
    }

    public Range(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    public Calendar getCalendarStart() {
        return start;
    }

    public Calendar getCalendarEnd() {
        return end;
    }

    public LocalDateTime getStart() {

        if (getCalendarStart() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarStart().toInstant(), ZoneId.systemDefault());
    }

    public LocalDateTime getEnd() {

        if (getCalendarEnd() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarEnd().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Returns the duration in minutes.
     */
    public Long getDuration() {
        if (start == null || end == null) {
            return null;
        }
        if (start.after(end)) {
            return 0l;
        }
        return (end.getTimeInMillis() - start.getTimeInMillis()) / 1000 / 60;
    }

    /**
     * Returns true, if the given calendar matches into the range.
     */
    public boolean matches(Calendar cal) {
        if (start == null && end == null) {
            return false;
        }
        long matchStart = start != null ? start.getTimeInMillis()
                : DateTimeUtils.truncateToMidnight(cal).getTimeInMillis();
        long matchEnd = end != null ? end.getTimeInMillis() : DateTimeUtils.endOfDayDate(cal).getTimeInMillis();
        return cal.getTimeInMillis() >= matchStart && cal.getTimeInMillis() < matchEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("start", DateTimeUtils.getDate(start))
                .append("end", DateTimeUtils.getDate(end)).toString();
    }
}
