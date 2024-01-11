package org.com.itpple.spot.server.exception.comment;

import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;

public class CommentPotNotMatchException extends CustomException {

    public CommentPotNotMatchException() {
        super(ErrorCode.COMMENT_POT_NOT_MATCH);
    }
}
