package org.com.itpple.spot.server.global.exception.dto;

public record ErrorResponse(
        Integer code,
        String message
) {
}
