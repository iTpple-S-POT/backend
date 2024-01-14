package org.com.itpple.spot.server.domain.location.dto;

import javax.validation.constraints.NotNull;

public record PointDTO(
        @NotNull Double lat,
        @NotNull Double lon) {

    public static PointDTO from(org.locationtech.jts.geom.Point point) {
        return new PointDTO(point.getY(), point.getX());
    }
}
