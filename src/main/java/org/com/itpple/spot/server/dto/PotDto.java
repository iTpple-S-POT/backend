package org.com.itpple.spot.server.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.entity.Pot;


@AllArgsConstructor
@Builder
@Getter
public class PotDto {

    private final Long id;
    private final Long userId;
    private final List<Long> categoryId;// 향후 여러 개의 카테고리를 가질 수 있음
    private final PotType potType;
    private final String content;
    private final Location location;
    private final String imageKey;
    private final LocalDateTime expiredAt;

    public static PotDto from(Pot pot) {
        return PotDto.builder()
                .id(pot.getId())
                .userId(pot.getUserId())
                .categoryId(List.of(pot.getCategoryId()))
                .potType(pot.getPotType())
                .content(pot.getContent())
                .location(Location.from(pot.getLocation()))
                .imageKey(pot.getImageKey())
                .expiredAt(pot.getExpiredAt())
                .build();
    }
}
