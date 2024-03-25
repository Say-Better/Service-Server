package io.say.better.global.auth.exception

import io.say.better.global.common.code.BaseErrorCode
import io.say.better.global.common.exception.GeneralException

class AuthException(code: BaseErrorCode) : GeneralException(code)
