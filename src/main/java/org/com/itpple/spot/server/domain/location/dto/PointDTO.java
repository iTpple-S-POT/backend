package org.com.itpple.spot.server.domain.location.dto;

import javax.validation.constraints.NotNull;
import org.locationtech.jts.geom.Point;

public record PointDTO(
        @NotNull Double lat,
        @NotNull Double lon) {

    public static PointDTO from(Point point) {
        return new PointDTO(point.getX(), point.getY());
    }
}
