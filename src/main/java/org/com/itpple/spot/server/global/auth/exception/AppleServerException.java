package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class AppleServerException extends BusinessException {

    public AppleServerException() {
        super(ErrorCode.APPLE_SERVER_ERROR);
    }
}
