package org.com.itpple.spot.server.domain.comment.dto.request;

import javax.validation.constraints.NotBlank;

public record CreateCommentRequest(
    Long parentCommentId,
    @NotBlank String content
) {
}
