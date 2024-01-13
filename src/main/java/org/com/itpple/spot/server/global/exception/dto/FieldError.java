package org.com.itpple.spot.server.global.exception.dto;

public record FieldError(
        String field,
        String value,
        String reason
) {
}
