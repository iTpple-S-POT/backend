package org.com.itpple.spot.server.dto.pot.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.dto.PointDTO;
import org.com.itpple.spot.server.entity.Pot;

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

    public static CreatePotResponse from(Pot Pot) {
        return CreatePotResponse.builder()
                .id(Pot.getId())
                .userId(Pot.getUser().getId())
                .categoryId(Pot.getCategoryId())
                .type(Pot.getPotType())
                .content(Pot.getContent())
                .imageKey(Pot.getImageKey())
                .location(new PointDTO(Pot.getLocation().getX(), Pot.getLocation().getY()))
                .expiredAt(Pot.getExpiredAt())
                .createdAt(Pot.getCreatedAt())
                .build();
    }
}
