package org.com.itpple.spot.server.domain.comment.exception;

import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class CommentPotNotMatchException extends BusinessException {

    public CommentPotNotMatchException() {
        super(ErrorCode.NOT_MATCH_COMMENT_POT);
    }
}
