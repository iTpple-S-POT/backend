package org.com.itpple.spot.server.dto.reaction.request;

import javax.validation.constraints.NotNull;
import org.com.itpple.spot.server.constant.ReactionType;

public record CreateReactionRequest(
    @NotNull Long potId,
    @NotNull ReactionType reactionType
) {
}
