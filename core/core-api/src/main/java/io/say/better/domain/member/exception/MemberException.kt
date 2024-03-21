package io.say.better.domain.member.exception;

import io.say.better.global.common.code.BaseErrorCode;
import io.say.better.global.common.exception.GeneralException;

public class MemberException extends GeneralException {

	public MemberException(BaseErrorCode code) {
		super(code);
	}

}
