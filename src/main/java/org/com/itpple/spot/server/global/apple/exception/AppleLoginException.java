package org.com.itpple.spot.server.global.apple.exception;

import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

public class AppleLoginException extends CustomException {

	public AppleLoginException() {
		super(ErrorCode.APPLE_LOGIN_ERROR);
	}
}
