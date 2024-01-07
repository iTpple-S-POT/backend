package org.com.itpple.spot.server.dto.pot.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.dto.Location;
import org.com.itpple.spot.server.entity.Pot;

@Builder
public record CreatePotResponse(
        Long id,
        Long userId,
        Long categoryId,
        PotType type,
        String content,
        String imageKey,
        Location location,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {

    public static CreatePotResponse from(Pot pot) {
        return CreatePotResponse.builder()
                .id(pot.getId())
                .userId(pot.getUser().getId())
                .categoryId(pot.getCategoryId())
                .type(pot.getPotType())
                .content(pot.getContent())
                .imageKey(pot.getImageKey())
                .location(new Location(pot.getLocation().getX(), pot.getLocation().getY()))
                .expiredAt(pot.getExpiredAt())
                .createdAt(pot.getCreatedAt())
                .build();
    }
}
