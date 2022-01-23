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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Holds eclipse informations.
 *
 * @author Gerhard Riegler - Initial contribution
 */
public class Eclipse {

    private Calendar total;
    private Calendar partial;

    public Calendar getCalendarTotal() {
        return total;
    }

    public LocalDateTime getTotal() {

        if (getCalendarTotal() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarTotal().toInstant(), ZoneId.systemDefault());
    }

    public void setTotal(Calendar total) {
        this.total = total;
    }

    public Calendar getCalendarPartial() {
        return partial;
    }

    public LocalDateTime getPartial() {

        if (getCalendarPartial() == null) {
            return null;
        }

        return LocalDateTime.ofInstant(getCalendarPartial().toInstant(), ZoneId.systemDefault());
    }

    public void setPartial(Calendar partial) {
        this.partial = partial;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("total", total == null ? null : total.getTime())
                .append("partial", partial == null ? null : partial.getTime()).toString();
    }
}