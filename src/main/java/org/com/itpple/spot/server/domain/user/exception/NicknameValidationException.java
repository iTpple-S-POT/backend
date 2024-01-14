package org.com.itpple.spot.server.domain.user.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class NicknameValidationException extends CustomException {
    public NicknameValidationException() {
        super(ErrorCode.NICKNAME_VALIDATION);
    }

}
