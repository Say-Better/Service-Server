package com.aesp.domain.temp.exception;

import com.aesp.global.common.code.BaseErrorCode;
import com.aesp.global.common.exception.GeneralException;

public class TempException extends GeneralException {
	public TempException(BaseErrorCode code) {
		super(code);
	}
}
