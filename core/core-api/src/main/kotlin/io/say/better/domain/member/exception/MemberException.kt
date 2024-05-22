package io.say.better.domain.member.exception

import io.say.better.global.common.code.BaseErrorCode
import io.say.better.global.common.exception.GeneralException

class MemberException(code: BaseErrorCode) : GeneralException(code)
