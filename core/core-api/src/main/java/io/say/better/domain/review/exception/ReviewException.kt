package io.say.better.domain.review.exception;

import io.say.better.global.common.code.BaseErrorCode;
import io.say.better.global.common.exception.GeneralException;

public class ReviewException extends GeneralException {

	public ReviewException(BaseErrorCode code) {
		super(code);
	}

}
