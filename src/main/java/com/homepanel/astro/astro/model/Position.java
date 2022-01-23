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
 * Holds the calculated azimuth and elevation.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Gaël L'hopital - Added shade length
 * @author Christoph Weitkamp - Introduced UoM
 */
public class Position {

    private Double azimuth;
    private Double elevation;
    private Double shadeLength;

    public Position() {
    }

    public Position(Double azimuth, Double elevation, Double shadeLength) {
        setAzimuth(azimuth);
        setElevation(elevation);
        setShadeLength(shadeLength);
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Double getShadeLength() {
        return shadeLength;
    }

    public void setShadeLength(Double shadeLength) {
        this.shadeLength = shadeLength;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("azimuth", azimuth)
                .append("elevation", elevation).append("shadeLength", shadeLength).toString();
    }
}
