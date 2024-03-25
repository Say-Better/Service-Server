package io.say.better.domain.solution.exception

import io.say.better.global.common.code.BaseErrorCode
import io.say.better.global.common.exception.GeneralException

class SolutionException(code: BaseErrorCode) : GeneralException(code)
