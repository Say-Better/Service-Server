package io.say.better.global.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:auth.yml")
data class JwtProperties(
    @Value("\${jwt.bearer}") val bearer: String,
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.access-expiration}") val accessExpiration: Long,
    @Value("\${jwt.access-header}") val accessHeader: String,
    @Value("\${jwt.refresh-expiration}") val refreshExpiration: Long,
    @Value("\${jwt.refresh-header}") val refreshHeader: String
)
