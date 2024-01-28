package com.saybetter.global.common.code.status;

import org.springframework.http.HttpStatus;

import com.saybetter.global.common.code.BaseErrorCode;
import com.saybetter.global.common.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	/* [ErrorStatus 작성 규칙]
        ErrorCode는 다음과 같은 형식으로 작성합니다.

        1. Success 및 Common Error
            HTTP_STATUS: HTTP_STATUS 는 HttpStatus Enum 을 참고하여 작성합니다.
                ex) _OK, _BAD_REQUEST, _UNAUTHORIZED, _FORBIDDEN, _METHOD_NOT_ALLOWED, _INTERNAL_SERVER_ERROR
            CODE: [CATEGORY]_[HTTP_STATUS_CODE]
                ex) SUCCESS_200, COMMON_400, COMMON_401, COMMON_403, COMMON_405, COMMON_500

        2. Other Error
            HTTP_STATUS: 에러의 상황을 잘 들어내는 HttpStatus 를 작성합니다.
                ex) USER_NOT_FOUND, USER_ALREADY_EXISTS
            CODE: [CATEGORY]_[HTTP_STATUS_CODE]_[ERROR_CODE]의 형식으로 작성합니다.
                ex) BAD_REQUEST -> USER_400_001,
                    NOT_FOUND -> USER_404_001,
                    ALREADY_EXISTS -> USER_409_001
     */

	// Common Error & Global Error
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
	METHOD_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "COMMON_400", "올바르지 않은 클라이언트 요청값입니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "지원하지 않는 Http Method 입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러가 발생했습니다."),

	// Temp Error
	TEMP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TEMP_500", "임시 에러가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ResponseDto.ErrorReasonDto getReason() {
		return ResponseDto.ErrorReasonDto.builder()
				.isSuccess(false)
				.code(this.code)
				.message(this.message)
				.build();
	}

	@Override
	public ResponseDto.ErrorReasonDto getReasonHttpStatus() {
		return ResponseDto.ErrorReasonDto.builder()
				.httpStatus(this.httpStatus)
				.isSuccess(false)
				.code(this.code)
				.message(this.message)
				.build();
	}
}
