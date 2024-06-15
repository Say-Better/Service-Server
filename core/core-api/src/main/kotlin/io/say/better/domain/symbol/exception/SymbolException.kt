package io.say.better.domain.symbol.exception

import io.say.better.core.common.code.BaseErrorCode
import io.say.better.core.common.exception.GeneralException

class SymbolException(
    code: BaseErrorCode,
) : GeneralException(code)
