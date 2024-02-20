package org.com.itpple.spot.server.domain.comment.dto.response;

import lombok.Builder;
import org.com.itpple.spot.server.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Builder
public record UpdateCommentResponse(
	LocalDateTime commentUpdatedAt,
	String content
) {

	public static UpdateCommentResponse from(Comment comment) {
		return UpdateCommentResponse.builder()
			.commentUpdatedAt(comment.getUpdatedAt())
			.content(comment.getContent())
			.build();
	}
}
