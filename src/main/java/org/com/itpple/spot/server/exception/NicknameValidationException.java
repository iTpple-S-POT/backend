package org.com.itpple.spot.server.exception;

import org.com.itpple.spot.server.exception.code.ErrorCode;

public class NicknameValidationException extends CustomException{
    public NicknameValidationException() {
        super(ErrorCode.NICKNAME_VALIDATION);
    }

}
