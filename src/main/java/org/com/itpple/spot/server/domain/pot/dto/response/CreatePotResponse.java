package org.com.itpple.spot.server.domain.pot.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.com.itpple.spot.server.global.common.constant.PotType;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.entity.Pot;

@Builder
public record CreatePotResponse(
        Long id,
        Long userId,
        Long categoryId,
        PotType type,
        String content,
        String imageKey,
        PointDTO location,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {

    public static CreatePotResponse from(Pot pot) {
        return CreatePotResponse.builder()
                .id(pot.getId())
                .userId(pot.getUser().getId())
                .categoryId(pot.getCategory().getId())
                .type(pot.getPotType())
                .content(pot.getContent())
                .imageKey(pot.getImageKey())
                .location(new PointDTO(pot.getLocation().getX(), pot.getLocation().getY()))
                .expiredAt(pot.getExpiredAt())
                .createdAt(pot.getCreatedAt())
                .build();
    }
}
