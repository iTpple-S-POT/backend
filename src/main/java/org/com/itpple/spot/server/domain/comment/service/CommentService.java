package org.com.itpple.spot.server.domain.comment.service;

import java.util.List;
import org.com.itpple.spot.server.domain.comment.dto.CommentDto;
import org.com.itpple.spot.server.domain.comment.dto.request.CreateCommentRequest;
import org.com.itpple.spot.server.domain.comment.dto.response.CreateCommentResponse;

public interface CommentService {

    CreateCommentResponse addComment(Long userId, Long potId, CreateCommentRequest request);

    List<CommentDto> getCommentList(Long userId, Long potId);
}
