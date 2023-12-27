package org.com.itpple.spot.server.dto;

import javax.validation.constraints.NotNull;

public record Location(
        @NotNull Double lat,
        @NotNull Double lon) {

    public static Location from(org.locationtech.jts.geom.Point point) {
        return new Location(point.getY(), point.getX());
    }
}
