package org.com.itpple.spot.server.domain.comment.service;

import java.util.List;
import org.com.itpple.spot.server.domain.comment.dto.CommentDto;
import org.com.itpple.spot.server.domain.comment.dto.request.CreateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.request.UpdateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.response.CreateCommentResponse;
import org.com.itpple.spot.server.domain.comment.dto.response.UpdateCommentResponse;

public interface CommentService {

    CreateCommentResponse addComment(Long userId, CreateCommentRequest request);

    List<CommentDto> getCommentList(Long userId, Long potId);

    UpdateCommentResponse updateComment(Long userId, Long commentId, UpdateCommentRequest request);

    void deleteComment(Long userId, Long commentId);
}
