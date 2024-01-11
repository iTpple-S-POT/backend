package org.com.itpple.spot.server.dto.comment.request;

import javax.validation.constraints.NotBlank;

public record CreateCommentRequest(
    Long parentCommentId,
    @NotBlank String content
) {
}
