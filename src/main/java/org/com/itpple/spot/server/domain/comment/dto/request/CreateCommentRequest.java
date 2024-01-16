package org.com.itpple.spot.server.domain.comment.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.NonNull;

public record CreateCommentRequest(
    @NonNull Long potId,
    Long parentCommentId,
    @NotBlank String content
) {
}
