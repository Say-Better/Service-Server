package io.say.better.global.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding

@ConfigurationProperties(prefix = "jwt")
@ConfigurationPropertiesBinding
@JvmRecord
data class JwtProperties(
    val bearer: String,
    val secret: String,
    val accessHeader: String,
    val accessExpiration: Long,
    val refreshExpiration: Long,
    val refreshHeader: String
)
