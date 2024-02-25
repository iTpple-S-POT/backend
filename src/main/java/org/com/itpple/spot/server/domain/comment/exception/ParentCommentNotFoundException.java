package org.com.itpple.spot.server.domain.comment.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class ParentCommentNotFoundException extends BusinessException {

    public ParentCommentNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_PARENT_COMMENT, optionalMessage);
    }
}
