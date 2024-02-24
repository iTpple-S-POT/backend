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
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> deleteReaction(
        @Auth CustomUserDetails customUserDetails,
        @PathVariable(value = "reactionId") Long reactionId
    ) {
        Long userId = customUserDetails.getUserId();
        reactionService.deleteReaction(userId, reactionId);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
    }
}
