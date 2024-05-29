package io.say.better.client.symbol.config

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class SymbolFeignConfiguration {

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor {
            it.header("Content-Type", "application/json; charset=UTF-8")
        }
    }
}
