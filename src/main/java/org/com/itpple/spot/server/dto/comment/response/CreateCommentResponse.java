package org.com.itpple.spot.server.dto.comment.response;

import java.time.LocalDateTime;
import lombok.Builder;
import org.com.itpple.spot.server.entity.Comment;
import org.com.itpple.spot.server.entity.User;

@Builder
public record CreateCommentResponse(
    String writerProfileImageUrl,
    String writerName,
    LocalDateTime commentUpdatedAt,
    String content
) {

    public static CreateCommentResponse from(Comment comment) {
        return CreateCommentResponse.builder()
            .writerProfileImageUrl(comment.getWriter().getProfileImageUrl())
            .writerName(comment.getWriter().getName())
            .commentUpdatedAt(comment.getUpdatedAt())
            .content(comment.getContent())
            .build();
    }
}
