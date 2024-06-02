package io.say.better.client.oauth.google.config

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class GoogleOauth2FeignConfiguration {
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor {
            it.header("Content-Type", "application/json; charset=UTF-8")
        }
    }
}
