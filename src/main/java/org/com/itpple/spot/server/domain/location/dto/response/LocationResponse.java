package org.com.itpple.spot.server.domain.location.dto.response;

import lombok.Builder;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.location.entity.Location;

import java.time.LocalDateTime;

@Builder
public record LocationResponse(
        Long userId,
        Long locationId,
        PointDTO location,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static LocationResponse fromLocation(Location location) {
        return LocationResponse.builder()
                .userId(location.getUser().getId())
                .locationId(location.getId())
                .location(new PointDTO(location.getLocation().getX(), location.getLocation().getY()))
                .createdAt(location.getCreatedAt())
                .updatedAt(location.getUpdatedAt())
                .build();
    }
}
