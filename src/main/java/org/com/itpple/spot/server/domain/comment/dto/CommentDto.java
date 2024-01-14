package org.com.itpple.spot.server.domain.comment.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import org.com.itpple.spot.server.domain.comment.entity.Comment;

@Builder
@Getter
public class CommentDto {

    private Long commentId;
    private String content;
    private LocalDateTime commentUpdatedAt;
    private UserDto writer;
    @Default
    private List<CommentDto> childrenComments = new ArrayList<>();

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class UserDto {
        private Long userId;
        private String profileImageUrl;
        private String name;
    }

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
            .commentId(comment.getId())
            .content(comment.isDeleted() ? "작성자가 삭제한 댓글입니다." : comment.getContent())
            .commentUpdatedAt(comment.getUpdatedAt())
            .writer(
                new UserDto(
                    comment.getWriter().getId(),
                    comment.getWriter().getProfileImageUrl(),
                    comment.getWriter().getName())
            )
            .build();
    }
}