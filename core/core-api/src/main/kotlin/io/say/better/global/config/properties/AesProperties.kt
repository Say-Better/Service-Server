package io.say.better.global.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:auth.yml")
data class AesProperties(
    @Value("\${auth.aes.key}") val aesKey: String,
    @Value("\${auth.aes.salt}") val salt: String,
)
