package org.com.itpple.spot.server.domain.reaction.dto.response;

import lombok.Builder;
import org.com.itpple.spot.server.global.common.constant.ReactionType;
import org.com.itpple.spot.server.domain.reaction.entity.Reaction;

@Builder
public record CreateReactionResponse(ReactionType reactionType) {

    public static CreateReactionResponse from(Reaction reaction) {
        return CreateReactionResponse.builder()
            .reactionType(reaction.getReactionType())
            .build();
    }
}
