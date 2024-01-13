package org.com.itpple.spot.server.global.exception.dto;

import java.util.List;

public record ValidationErrorResponse(
        Integer code,
        String message,
        List<FieldError> errors
) {
}
