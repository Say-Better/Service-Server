package com.saybetter.domain.review.exception;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.exception.GeneralException;

public class ReviewException extends GeneralException {

	public ReviewException(BaseErrorCode code) {
		super(code);
	}
	
}
