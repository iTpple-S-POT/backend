package org.com.itpple.spot.server.exception.dto;

public record FieldError(
        String field,
        String value,
        String reason
) {
}
