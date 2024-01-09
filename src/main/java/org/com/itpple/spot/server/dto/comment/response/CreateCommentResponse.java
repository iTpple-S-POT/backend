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

    public static CreateCommentResponse from(User user, Comment comment) {
        return CreateCommentResponse.builder()
            .writerProfileImageUrl(user.getProfileImageUrl())
            .writerName(user.getName())
            .commentUpdatedAt(comment.getUpdatedAt())
            .content(comment.getContent())
            .build();
    }
}
