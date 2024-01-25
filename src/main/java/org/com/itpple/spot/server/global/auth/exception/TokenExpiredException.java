package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class TokenExpiredException extends CustomException {

	public TokenExpiredException() {
		super(ErrorCode.TOKEN_EXPIRED);
	}
}
