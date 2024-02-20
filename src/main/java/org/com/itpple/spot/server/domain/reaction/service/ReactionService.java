package org.com.itpple.spot.server.domain.reaction.service;

import org.com.itpple.spot.server.domain.reaction.dto.request.CreateReactionRequest;
import org.com.itpple.spot.server.domain.reaction.dto.response.CreateReactionResponse;

public interface ReactionService {

    CreateReactionResponse addReaction(Long userId, CreateReactionRequest request);

	void deleteReaction(Long userId, Long reactionId);
}
