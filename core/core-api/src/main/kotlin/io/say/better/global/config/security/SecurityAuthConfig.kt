package io.say.better.global.config.security

import io.say.better.global.config.properties.AesProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.encrypt.AesBytesEncryptor

@Configuration
class SecurityAuthConfig(
    private val aesProperties: AesProperties,
) {
    @Bean
    fun aesBytesEncryptor(): AesBytesEncryptor = AesBytesEncryptor(aesProperties.aesKey, aesProperties.salt)
}
