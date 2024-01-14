package org.com.itpple.spot.server.domain.user.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class NicknameDuplicateException extends CustomException {
    public NicknameDuplicateException() {
        super(ErrorCode.NICKNAME_DUPLICATE);
    }
}
