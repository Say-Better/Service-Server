package com.saybetter.domain.symbol.exception;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.exception.GeneralException;

public class SymbolException extends GeneralException {

	public SymbolException(BaseErrorCode code) {
		super(code);
	}

}
