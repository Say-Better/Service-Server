package io.say.better.global.config.web

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@RequiredArgsConstructor
open class CorsConfig {
    val corsConfigurationSource: CorsConfigurationSource
        get() {
            val configuration = CorsConfiguration()
            configuration.allowedOrigins = mutableListOf("http://localhost:3000", "http://localhost:8080")
            configuration.allowedMethods = mutableListOf("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")

            val source = UrlBasedCorsConfigurationSource()
            source.registerCorsConfiguration("/**", configuration)
            return source
        }
}
