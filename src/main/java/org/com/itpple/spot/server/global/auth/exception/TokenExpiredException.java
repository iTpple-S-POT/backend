package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class TokenExpiredException extends BusinessException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
