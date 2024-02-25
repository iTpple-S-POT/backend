package org.com.itpple.spot.server.domain.user.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class NicknameDuplicateException extends BusinessException {
    public NicknameDuplicateException() {
        super(ErrorCode.NICKNAME_DUPLICATE);
    }
}
