package org.com.itpple.spot.server.dto.reaction.response;

import lombok.Builder;
import org.com.itpple.spot.server.constant.ReactionType;
import org.com.itpple.spot.server.entity.Reaction;

@Builder
public record CreateReactionResponse(ReactionType reactionType) {

    public static CreateReactionResponse from(Reaction reaction) {
        return CreateReactionResponse.builder()
            .reactionType(reaction.getReactionType())
            .build();
    }
}
