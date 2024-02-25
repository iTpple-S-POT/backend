package org.com.itpple.spot.server.domain.comment.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class CommentIdNotFoundException extends BusinessException {

    public CommentIdNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_COMMENT, optionalMessage);
    }
}
