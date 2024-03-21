package io.say.better.global.auth.exception;

import io.say.better.global.common.code.BaseErrorCode;
import io.say.better.global.common.exception.GeneralException;

public class AuthException extends GeneralException {

	public AuthException(BaseErrorCode code) {
		super(code);
	}

}
