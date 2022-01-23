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
package com.homepanel.astro.service;

public final class AstroConstants {

    public enum PLANET {
        SUN,
        MOON
    }

    public enum GROUP {
        RISE,
        SET,
        NOON,
        NIGHT,
        MORNING_NIGHT,
        ASTRO_DAWN,
        NAUTIC_DAWN,
        CLIVIL_DAWN,
        ASTRO_DUSK,
        NAUTIC_DUSK,
        CILIVL_DUSK,
        EVENING_NIGHT,
        DAY_LIGHT,
        POSITION,
        RADIATION,
        ZODIAC,
        SEASON,
        ECLIPSE,
        PHASE,
        DISTANCE,
        PERIGEE,
        APOGEE
    }

    public enum CHANNEL {
        START,
        END,
        AZIMUTH,
        EVELATION,
        SHADE_LENGTH,
        DIRECT,
        DIFFUSE,
        TOTAL,
        SIGN,
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER,
        PARTIAL,
        RING,
        NAME,
        FIRST_QUATER,
        THIRD_QUATER,
        FULL,
        NEW,
        AGE,
        AGE_DEGREE,
        AGE_PERCENT,
        ILLUMINATION,
        DATE,
        DISTANCE
    }
}