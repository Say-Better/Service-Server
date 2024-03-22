package io.say.better.global.advice

import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@DependsOn(value = ["TxAdvice"])
class Tx(
    private val _txAdvice: TxAdvice,
) {

    init {
        txAdvice = _txAdvice
    }

    companion object {
        private lateinit var txAdvice: TxAdvice

        fun <T> writeable(function: () -> T): T {
            return txAdvice.writeable(function)
        }

        fun <T> readable(function: () -> T): T {
            return txAdvice.readable(function)
        }
    }

    @Component("TxAdvice")
    open class TxAdvice {

        @Transactional
        open fun <T> writeable(function: () -> T): T {
            return function()
        }

        @Transactional(readOnly = true)
        open fun <T> readable(function: () -> T): T {
            return function()
        }
    }
}
