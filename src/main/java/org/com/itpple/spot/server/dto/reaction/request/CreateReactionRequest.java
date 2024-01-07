package org.com.itpple.spot.server.dto.reaction.request;

import javax.validation.constraints.NotNull;
import org.com.itpple.spot.server.common.validator.EnumValid;
import org.com.itpple.spot.server.constant.ReactionType;

public record CreateReactionRequest(
    @NotNull Long potId,
    @EnumValid(enumClass = ReactionType.class) String reactionType
) {
}
