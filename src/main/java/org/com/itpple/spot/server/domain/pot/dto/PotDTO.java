package org.com.itpple.spot.server.domain.pot.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.global.common.constant.PotType;

/**
 * response 반환용 뿐 아니라 다른 서비스에서 사용할 수 있도록 DTO로 분리
 */
@AllArgsConstructor
@Builder
@Getter
public class PotDTO {

    private final Long id;
    private final Long userId;
    private final List<Long> categoryId;// 향후 여러 개의 카테고리를 가질 수 있음
    private final PotType potType;
    private final String content;
    private final PointDTO location;
    private final String imageKey;
    private final LocalDateTime expiredAt;
    private final List<HashtagDTO> hashtagList;
    private final Long viewCount;
    public static PotDTO from(Pot pot) {
        return PotDTO.builder()
                .id(pot.getId())
                .userId(pot.getUser().getId())
                .categoryId(List.of(pot.getCategory().getId()))
                .potType(pot.getPotType())
                .content(pot.getContent())
                .location(PointDTO.from(pot.getLocation()))
                .imageKey(pot.getImageKey())
                .expiredAt(pot.getExpiredAt())
                .hashtagList(pot.getPotHashtagList().stream()
                        .map(potHashtag -> HashtagDTO.from(potHashtag.getHashtag())
                        ).toList())
                .viewCount(pot.getViewCount())
                .build();
    }
}
