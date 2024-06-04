package io.say.better.global.common.code.status

import io.say.better.global.common.code.BaseErrorCode
import io.say.better.global.common.response.ResponseDto.ErrorReasonDto
import org.springframework.http.HttpStatus

/** [ErrorStatus 작성 규칙]
*   ErrorCode는 다음과 같은 형식으로 작성합니다.
*
*   1. Success 및 Common Error
*       HTTP_STATUS: HTTP_STATUS 는 HttpStatus Enum 을 참고하여 작성합니다.
*           ex) _OK, _BAD_REQUEST, _UNAUTHORIZED, _FORBIDDEN, _METHOD_NOT_ALLOWED, _INTERNAL_SERVER_ERROR
*       CODE: [CATEGORY]_[HTTP_STATUS_CODE]
*           ex) SUCCESS_200, COMMON_400, COMMON_401, COMMON_403, COMMON_405, COMMON_500
*
*   2. Other Error
*       HTTP_STATUS: 에러의 상황을 잘 들어내는 HttpStatus 를 작성합니다.
*           ex) USER_NOT_FOUND, USER_ALREADY_EXISTS
*       CODE: [CATEGORY]_[HTTP_STATUS_CODE]_[ERROR_CODE]의 형식으로 작성합니다.
*           ex) BAD_REQUEST -> USER_400_001,
*               NOT_FOUND -> USER_404_001,
*               ALREADY_EXISTS -> USER_409_001
 */
enum class ErrorStatus(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String,
) : BaseErrorCode {
    // Common Error & Global Error
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    METHOD_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "COMMON_400", "올바르지 않은 클라이언트 요청값입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "지원하지 않는 Http Method 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러가 발생했습니다."),

    // Temp Error
    TEMP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TEMP_500", "임시 에러가 발생했습니다."),

    // Member Error
    MEMBER_HAVE_ROLE_SIGN(HttpStatus.BAD_REQUEST, "MEMBER_400_401", "이미 권한이 부여된 회원입니다."),
    CONNECT_CODE_NOT_VALID(HttpStatus.BAD_REQUEST, "MEMBER_400_402", "유효하지 않은 연결 코드입니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404_001", "해당 회원을 찾을 수 없습니다."),
    MEMBER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404_002", "해당 이메일을 가진 회원이 존재하지 않습니다."),

    // Educator Error
    EDUCATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "EDUCATOR_404_001", "해당 교육자를 찾을 수 없습니다."),

    // Learner Error
    LEARNER_NOT_FOUND(HttpStatus.NOT_FOUND, "LEARNER_404_001", "해당 학습자를 찾을 수 없습니다."),

    // Solution Error
    START_SOLUTION_NOT_FOUND(HttpStatus.NOT_FOUND, "SOLUTION_404_001", "시작 요청한 솔루션을 찾을 수 없습니다."),
    END_PROGRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "SOLUTION_404_002", "솔루션 진행 데이터를 찾을 수 없습니다."),
    ;

    override val reason: ErrorReasonDto
        get() =
            ErrorReasonDto(
                isSuccess = false,
                code = this.code,
                message = this.message,
            )

    override val reasonHttpStatus: ErrorReasonDto
        get() =
            ErrorReasonDto(
                httpStatus = this.httpStatus,
                isSuccess = false,
                code = this.code,
                message = this.message,
            )
}
