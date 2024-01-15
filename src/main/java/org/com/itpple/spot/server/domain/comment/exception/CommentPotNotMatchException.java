package org.com.itpple.spot.server.domain.comment.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class CommentPotNotMatchException extends CustomException {

    public CommentPotNotMatchException() {
        super(ErrorCode.NOT_MATCH_COMMENT_POT);
    }
}