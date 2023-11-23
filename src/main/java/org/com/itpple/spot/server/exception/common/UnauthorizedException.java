package org.com.itpple.spot.server.exception.common;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class UnauthorizedException extends CustomException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }

    public UnauthorizedException(ErrorCode errorCode, String optionalMessage) {
        super(HttpStatus.UNAUTHORIZED, errorCode, optionalMessage);
    }
}
