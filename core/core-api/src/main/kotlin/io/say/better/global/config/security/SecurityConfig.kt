package io.say.better.global.config.security

import io.say.better.core.enums.RoleType
import io.say.better.global.auth.handler.OAuth2LoginFailureHandler
import io.say.better.global.auth.handler.OAuth2LoginSuccessHandler
import io.say.better.global.auth.service.CustomOAuth2UserService
import io.say.better.global.config.properties.JwtProperties
import io.say.better.global.config.web.CorsConfig
import io.say.better.global.jwt.filter.JwtAuthenticationProcessingFilter
import io.say.better.global.jwt.service.JwtService
import io.say.better.storage.mysql.dao.repository.MemberReadRepository
import io.say.better.storage.redis.RedisUtil
import lombok.RequiredArgsConstructor
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter

@Configuration
@RequiredArgsConstructor
open class SecurityConfig(
        private val corsConfig: CorsConfig,
        private val redisUtil: RedisUtil,
        private val jwtService: JwtService,
        private val jwtProperties: JwtProperties,
        private val memberReadRepository: MemberReadRepository,
        private val customOAuth2UserService: CustomOAuth2UserService,
        private val OAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
        private val OAuth2LoginFailureHandler: OAuth2LoginFailureHandler
) {

    private val permitUrls = arrayOf(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/h2-console/**",
            "/api/temp/**",
            "/api/auth/**",
            "/sing-up",
            "/api/tests/**",
    )

    private val noneUserRoleUrls = arrayOf(
            "/api/auth/assign/**"
    )

    private val educatorUrls = arrayOf(
            "/api/educator/**"
    )

    private val leanerUrls = arrayOf(
            "/api/learner/**"
    )

    @Bean
    @Throws(Exception::class)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { it.disable() }
                .formLogin { it.disable() }
                .cors { it.configurationSource(corsConfig.corsConfigurationSource) }
                .authorizeHttpRequests {
                    it
                            .requestMatchers(*permitUrls).permitAll()
                            .requestMatchers(*noneUserRoleUrls).hasRole(RoleType.NONE.name)
                            .requestMatchers(*educatorUrls).hasRole(RoleType.EDUCATOR.name)
                            .requestMatchers(*leanerUrls).hasRole(RoleType.LEARNER.name)
                            .anyRequest().authenticated()
                }
                .oauth2Login {
                    it
                            .userInfoEndpoint { it.userService(customOAuth2UserService) }
                            .successHandler(OAuth2LoginSuccessHandler)
                            .failureHandler(OAuth2LoginFailureHandler)
                }
                .headers {
                    it
                            .frameOptions { it.sameOrigin() }
                }
                .addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter::class.java)
        return http.build()
    }

    @Bean
    open fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it
                    .ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }

    @Bean
    open fun jwtAuthenticationProcessingFilter(): JwtAuthenticationProcessingFilter {
        return JwtAuthenticationProcessingFilter(
                memberReadRepository, jwtProperties, jwtService, redisUtil
        )
    }
}
