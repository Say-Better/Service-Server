package io.say.better.domain.solution.exception

import io.say.better.core.common.code.BaseErrorCode
import io.say.better.core.common.exception.GeneralException

class SolutionException(
    code: BaseErrorCode,
) : GeneralException(code)
