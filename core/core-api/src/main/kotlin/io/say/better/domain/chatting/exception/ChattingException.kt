package io.say.better.domain.chatting.exception

import io.say.better.core.common.code.BaseErrorCode
import io.say.better.core.common.exception.GeneralException

class ChattingException(
    code: BaseErrorCode,
) : GeneralException(code)
