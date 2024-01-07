package org.com.itpple.spot.server.exception;

import org.com.itpple.spot.server.exception.code.ErrorCode;

public class NicknameDuplicateException extends CustomException{
    public NicknameDuplicateException() {
        super(ErrorCode.NICKNAME_DUPLICATE);
    }
}
