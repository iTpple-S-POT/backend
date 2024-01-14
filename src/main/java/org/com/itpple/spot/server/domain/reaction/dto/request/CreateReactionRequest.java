package org.com.itpple.spot.server.domain.reaction.dto.request;

import javax.validation.constraints.NotNull;
import org.com.itpple.spot.server.global.common.validator.EnumValid;
import org.com.itpple.spot.server.global.common.constant.ReactionType;

public record CreateReactionRequest(
    @NotNull Long potId,
    @EnumValid(enumClass = ReactionType.class) String reactionType
) {
}
