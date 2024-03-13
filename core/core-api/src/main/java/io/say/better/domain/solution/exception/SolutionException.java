package io.say.better.domain.solution.exception;

import io.say.better.global.common.code.BaseErrorCode;
import io.say.better.global.common.exception.GeneralException;

public class SolutionException extends GeneralException {

	public SolutionException(BaseErrorCode code) {
		super(code);
	}

}
