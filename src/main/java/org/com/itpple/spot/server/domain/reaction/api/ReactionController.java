package org.com.itpple.spot.server.domain.reaction.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.global.auth.Auth;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.domain.reaction.dto.request.CreateReactionRequest;
import org.com.itpple.spot.server.domain.reaction.dto.response.CreateReactionResponse;
import org.com.itpple.spot.server.domain.reaction.service.ReactionService;
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

    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<CreateReactionResponse> addReaction(
        @Auth CustomUserDetails customUserDetails,
        @Valid @RequestBody CreateReactionRequest createReactionRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reactionService.addReaction(userId, createReactionRequest));
    }
}
