package io.say.better.domain.member.exception

import io.say.better.core.common.code.BaseErrorCode
import io.say.better.core.common.exception.GeneralException

class MemberException(
    code: BaseErrorCode,
) : GeneralException(code)
