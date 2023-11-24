package org.com.itpple.spot.server.exception.common;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends CustomException {

    public ForbiddenException(ErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, errorCode);
    }

    public ForbiddenException(ErrorCode errorCode, String optionalMessage) {
        super(HttpStatus.FORBIDDEN, errorCode, optionalMessage);
    }
}
