package org.com.itpple.spot.server.exception;

import org.com.itpple.spot.server.exception.code.ErrorCode;

public class MemberIdAlreadyExistsException extends CustomException{
    public MemberIdAlreadyExistsException() {
        super(ErrorCode.MEMBER_ID_ALREADY_EXISTS);
    }
}
