package org.com.itpple.spot.server.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.common.auth.Auth;
import org.com.itpple.spot.server.common.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.dto.reaction.request.CreateReactionRequest;
import org.com.itpple.spot.server.dto.reaction.response.CreateReactionResponse;
import org.com.itpple.spot.server.service.impl.ReactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
@RestController
public class ReactionController {

    private final ReactionServiceImpl reactionServiceImpl;

    @PostMapping
    public ResponseEntity<CreateReactionResponse> addReaction(
        @Auth CustomUserDetails customUserDetails,
        @Valid @RequestBody CreateReactionRequest createReactionRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reactionServiceImpl.addReaction(userId, createReactionRequest));
    }
}
