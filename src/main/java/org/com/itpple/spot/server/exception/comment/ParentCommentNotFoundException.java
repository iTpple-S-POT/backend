package org.com.itpple.spot.server.exception.comment;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;

public class ParentCommentNotFoundException extends CustomException {

    public ParentCommentNotFoundException(String optionalMessage) {
        super(ErrorCode.NOT_FOUND_PARENT_COMMENT, optionalMessage);
    }
}
