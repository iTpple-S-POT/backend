package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.reaction.request.CreateReactionRequest;
import org.com.itpple.spot.server.dto.reaction.response.CreateReactionResponse;

public interface ReactionService {

    CreateReactionResponse addReaction(Long userId, CreateReactionRequest request);
}
