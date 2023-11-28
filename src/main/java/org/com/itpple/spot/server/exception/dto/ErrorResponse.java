package org.com.itpple.spot.server.exception.dto;

public record ErrorResponse(
        Integer code,
        String message
) {
}
