package io.say.better.domain.review.exception

import io.say.better.core.common.code.BaseErrorCode
import io.say.better.core.common.exception.GeneralException

class ReviewException(
    code: BaseErrorCode,
) : GeneralException(code)
