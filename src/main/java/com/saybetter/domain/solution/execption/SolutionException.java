package com.saybetter.domain.solution.execption;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.exception.GeneralException;

public class SolutionException extends GeneralException {

	public SolutionException(BaseErrorCode code) {
		super(code);
	}

}
