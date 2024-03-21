package io.say.better.domain.symbol.exception;

import io.say.better.global.common.code.BaseErrorCode;
import io.say.better.global.common.exception.GeneralException;

public class SymbolException extends GeneralException {

	public SymbolException(BaseErrorCode code) {
		super(code);
	}

}
