package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.comment.request.CreateCommentRequest;
import org.com.itpple.spot.server.dto.comment.response.CreateCommentResponse;

public interface CommentService {

    CreateCommentResponse addComment(Long userId, Long potId, CreateCommentRequest request);
}
