package io.say.better.global.config.security

import io.say.better.core.common.constant.RoleType
import io.say.better.global.config.properties.JwtProperties
import io.say.better.global.config.web.CorsConfig
import io.say.better.global.jwt.filter.JwtAuthenticationProcessingFilter
import io.say.better.global.jwt.service.JwtService
import io.say.better.storage.mysql.domains.account.repository.MemberReadRepository
import io.say.better.storage.redis.RedisUtil
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter

@Configuration
class SecurityConfig(
    private val corsConfig: CorsConfig,
    private val redisUtil: RedisUtil,
    private val jwtService: JwtService,
    private val jwtProperties: JwtProperties,
    private val memberReadRepository: MemberReadRepository,
) {
    private val permitUrls =
        arrayOf(
            "/api/temp/**",
            "/api/auth/**",
            "/sing-up",
            "/api/tests/**",
            "/docs/**",
        )

    private val noneUserRoleUrls =
        arrayOf(
            "/api/auth/assign/**",
        )

    private val educatorUrls =
        arrayOf(
            "/api/educator/**",
        )

    private val leanerUrls =
        arrayOf(
            "/api/learner/**",
        )

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(corsConfig.corsConfigurationSource) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*permitUrls)
                    .permitAll()
                    .requestMatchers(*noneUserRoleUrls)
                    .hasRole(RoleType.NONE.name)
                    .requestMatchers(*educatorUrls)
                    .hasRole(RoleType.EDUCATOR.name)
                    .requestMatchers(*leanerUrls)
                    .hasRole(RoleType.LEARNER.name)
                    .requestMatchers(*educatorUrls, *leanerUrls)
                    .hasRole(RoleType.EDUCATOR_LEARNER.name)
                    .anyRequest()
                    .authenticated()
            }.headers {
                it
                    .frameOptions { it.sameOrigin() }
            }.addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter::class.java)
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer =
        WebSecurityCustomizer {
            it
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }

    @Bean
    fun jwtAuthenticationProcessingFilter(): JwtAuthenticationProcessingFilter =
        JwtAuthenticationProcessingFilter(
            memberReadRepository,
            jwtProperties,
            jwtService,
            redisUtil,
        )
}
