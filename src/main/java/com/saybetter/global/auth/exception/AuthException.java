package com.saybetter.global.auth.exception;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.exception.GeneralException;

public class AuthException extends GeneralException {

	public AuthException(BaseErrorCode code) {
		super(code);
	}

}
