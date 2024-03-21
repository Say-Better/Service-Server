package io.say.better.domain.symbol.exception

import io.say.better.global.common.code.BaseErrorCode
import io.say.better.global.common.exception.GeneralException

class SymbolException(code: BaseErrorCode) : GeneralException(code)
