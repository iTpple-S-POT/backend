package org.com.itpple.spot.server.domain.pot.dto;

import lombok.Builder;
import lombok.Getter;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;

@Builder
@Getter
public class HashtagDTO {
    Long hashtagId;
    String hashtag;

    public static HashtagDTO from(Hashtag hashtag) {
        return HashtagDTO.builder()
            .hashtagId(hashtag.getId())
            .hashtag(hashtag.getHashtag())
            .build();
    }
}
