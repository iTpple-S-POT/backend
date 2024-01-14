package org.com.itpple.spot.server.domain.location.dto.response;

import lombok.Builder;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.location.entity.Location;

@Builder
public record LocationResponse(PointDTO location) {
    public static LocationResponse fromLocation(Location location) {
        return LocationResponse.builder()
                .location(new PointDTO(location.getLocation().getX(), location.getLocation().getY()))
                .build();
    }
}
