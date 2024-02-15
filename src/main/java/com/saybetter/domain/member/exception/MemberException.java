package com.saybetter.domain.member.exception;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.exception.GeneralException;

public class MemberException extends GeneralException {

	public MemberException(BaseErrorCode code) {
		super(code);
	}

}
