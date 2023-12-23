package org.com.itpple.spot.server.dto.pot.request;

import static org.com.itpple.spot.server.constant.Constant.IMAGE_NAME_REGEX;
import static org.com.itpple.spot.server.constant.Constant.POT_EXPIRED_DAYS;
import static org.com.itpple.spot.server.util.GeometryUtil.convertToPoint;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.com.itpple.spot.server.constant.PotType;
import org.com.itpple.spot.server.dto.PointDTO;
import org.com.itpple.spot.server.entity.Pot;

public record CreatePotRequest(
        @NotNull Long categoryId,
        @NotNull @Pattern(regexp = IMAGE_NAME_REGEX, message = "Invalid image extension") String imageKey,
        @NotNull PotType type,
        @NotNull PointDTO location,
        String content
) {
    public static Pot toPot(CreatePotRequest createPotRequest) {
        return Pot.builder()
                .categoryId(createPotRequest.categoryId())
                .imageKey(createPotRequest.imageKey())
                .potType(createPotRequest.type())
                .content(createPotRequest.type() == PotType.TEXT ? createPotRequest.content() : null)
                .expiredAt(LocalDateTime.now().plusDays(POT_EXPIRED_DAYS))
                .location(convertToPoint(createPotRequest.location()))
                .build();
    }
}
