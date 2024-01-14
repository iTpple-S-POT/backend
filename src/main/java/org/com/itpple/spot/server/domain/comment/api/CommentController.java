package org.com.itpple.spot.server.domain.comment.api;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.global.auth.Auth;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.domain.comment.dto.CommentDto;
import org.com.itpple.spot.server.domain.comment.dto.request.CreateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.response.CreateCommentResponse;
import org.com.itpple.spot.server.domain.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> addComment(
        @Auth CustomUserDetails customUserDetails,
        @RequestParam(name = "potId") Long potId,
        @Valid @RequestBody CreateCommentRequest createCommentRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.addComment(userId, potId, createCommentRequest));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(
        @Auth CustomUserDetails customUserDetails,
        @RequestParam(name = "potId") Long potId
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentList(userId, potId));
    }
}
