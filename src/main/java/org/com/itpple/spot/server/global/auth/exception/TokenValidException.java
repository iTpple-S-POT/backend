package org.com.itpple.spot.server.global.auth.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class TokenValidException extends CustomException {

	public TokenValidException() {
		super(ErrorCode.TOKEN_NOT_VALID);
	}
}
