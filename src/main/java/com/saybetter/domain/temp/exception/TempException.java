package com.saybetter.domain.temp.exception;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.exception.GeneralException;

public class TempException extends GeneralException {
	public TempException(BaseErrorCode code) {
		super(code);
	}
}
