package org.com.itpple.spot.server.exception.common;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class InternalServerException extends CustomException {

    public InternalServerException(ErrorCode errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    public InternalServerException(ErrorCode errorCode, String optionalMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, optionalMessage);
    }
}