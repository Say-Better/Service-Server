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
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.UserInfoEndpointConfig
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

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

    @Bean
    @Throws(Exception::class)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .formLogin { obj: FormLoginConfigurer<HttpSecurity> -> obj.disable() }
            .cors { corsCustomizer: CorsConfigurer<HttpSecurity?> ->
                corsCustomizer.configurationSource(corsConfig.corsConfigurationSource)
            }
            .authorizeHttpRequests(Customizer<AuthorizationManagerRequestMatcherRegistry> { authorizeRequest: AuthorizationManagerRequestMatcherRegistry ->
                authorizeRequest
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/api/temp/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/oauth2/**")).permitAll()
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/assign/**"))
                    .hasRole(RoleType.NONE.name).requestMatchers(AntPathRequestMatcher.antMatcher("/api/**"))
                    .authenticated()
            }).oauth2Login { oauth2Login: OAuth2LoginConfigurer<HttpSecurity?> ->
                oauth2Login.userInfoEndpoint(Customizer { userInfoEndpoint: UserInfoEndpointConfig ->
                    userInfoEndpoint.userService(
                        customOAuth2UserService
                    )
                }).successHandler(OAuth2LoginSuccessHandler).failureHandler(OAuth2LoginFailureHandler)
            }.headers { headersConfigurer: HeadersConfigurer<HttpSecurity?> ->
                headersConfigurer.frameOptions(Customizer<FrameOptionsConfig> { FrameOptionsConfig.sameOrigin() })
            }.addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter::class.java)
        return http.build()
    }

    @Bean
    open fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
            )
        }
    }

    @Bean
    open fun jwtAuthenticationProcessingFilter(): JwtAuthenticationProcessingFilter {
        return JwtAuthenticationProcessingFilter(
            memberReadRepository, jwtProperties, jwtService, redisUtil
        )
    }
}
