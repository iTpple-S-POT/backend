package org.com.itpple.spot.server.exception.dto;

import java.util.List;

public record ValidationErrorResponse(
        Integer code,
        String message,
        List<FieldError> errors
) {
}
