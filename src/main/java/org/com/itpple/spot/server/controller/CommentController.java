package org.com.itpple.spot.server.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.common.auth.Auth;
import org.com.itpple.spot.server.common.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.dto.comment.CommentDto;
import org.com.itpple.spot.server.dto.comment.request.CreateCommentRequest;
import org.com.itpple.spot.server.dto.comment.response.CreateCommentResponse;
import org.com.itpple.spot.server.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{potId}")
    public ResponseEntity<CreateCommentResponse> addComment(
        @Auth CustomUserDetails customUserDetails,
        @PathVariable Long potId,
        @Valid @RequestBody CreateCommentRequest createCommentRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.addComment(userId, potId, createCommentRequest));
    }

    @GetMapping("/{potId}")
    public ResponseEntity<List<CommentDto>> getComments(
        @Auth CustomUserDetails customUserDetails,
        @PathVariable Long potId
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentList(userId, potId));
    }
}
