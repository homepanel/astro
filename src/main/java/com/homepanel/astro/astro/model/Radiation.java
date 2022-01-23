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

/**
 * Holds the calculated direct, diffuse and total
 *
 * @author Gaël L'hopital - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 */
public class Radiation {

    private Double direct;
    private Double diffuse;
    private Double total;

    public Radiation() {
    }

    public Radiation(double direct, double diffuse, double total) {
        setDirect(direct);
        setDiffuse(diffuse);
        setTotal(total);
    }

    public Double getDirect() {
        return direct;
    }

    public void setDirect(Double direct) {
        this.direct = direct;
    }

    public Double getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Double diffuse) {
        this.diffuse = diffuse;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("direct", direct)
                .append("diffuse", diffuse).append("total", total).toString();
    }
}