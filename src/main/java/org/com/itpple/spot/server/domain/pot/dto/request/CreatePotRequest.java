package org.com.itpple.spot.server.domain.pot.dto.request;

import static org.com.itpple.spot.server.global.common.constant.Constant.IMAGE_NAME_REGEX;
import static org.com.itpple.spot.server.global.common.constant.Constant.POT_EXPIRED_DAYS;
import static org.com.itpple.spot.server.global.util.GeometryUtil.createPoint;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.com.itpple.spot.server.domain.location.dto.PointDTO;
import org.com.itpple.spot.server.domain.pot.domain.category.entity.Category;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.global.common.constant.PotType;

public record CreatePotRequest(
        @NotNull Long categoryId,
        @NotNull @Pattern(regexp = IMAGE_NAME_REGEX, message = "Invalid image extension") String imageKey,
        @NotNull PotType type,
        @NotNull PointDTO location,
        String content,
        List<Long> hashtagIdList
) {

    public static Pot toPot(CreatePotRequest createPotRequest, User user, Category category, List<Hashtag> hashtagList) {
        var newPot = Pot.builder()
                .category(category)
                .user(user)
                .imageKey(createPotRequest.imageKey())
                .potType(createPotRequest.type())
                .content(createPotRequest.content())
                .expiredAt(LocalDateTime.now().plusDays(POT_EXPIRED_DAYS))
                .location(createPoint(createPotRequest.location()))
                .build();

        newPot.addHashtagList(hashtagList);

        return newPot;
    }
}
