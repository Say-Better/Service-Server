package io.say.better.global.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.say.better.storage.mysql.dao.repository.MemberReadRepository;
import io.say.better.global.auth.service.CustomOAuth2UserService;
import io.say.better.core.enums.RoleType;
import io.say.better.global.config.properties.JwtProperties;
import io.say.better.global.config.web.CorsConfig;
import io.say.better.global.jwt.filter.JwtAuthenticationProcessingFilter;
import io.say.better.global.jwt.service.JwtService;
import io.say.better.storage.redis.RedisUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsConfig;

	private final RedisUtil redisUtil;

	private final JwtService jwtService;
	private final JwtProperties jwtProperties;

	private final MemberReadRepository memberReadRepository;

	private final CustomOAuth2UserService customOAuth2UserService;
	private final io.say.better.global.auth.handler.OAuth2LoginSuccessHandler OAuth2LoginSuccessHandler;
	private final io.say.better.global.auth.handler.OAuth2LoginFailureHandler OAuth2LoginFailureHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.cors(corsCustomizer ->
						corsCustomizer
								.configurationSource(corsConfig.getCorsConfigurationSource())
				)
				.authorizeHttpRequests(authorizeRequest ->
						authorizeRequest
								/* 권한 관련
								   permitAll : 모든 요청 허용
								   authenticated : 권한 요청 필요
								 */
								.requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**"))
								.permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**"))
								.permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
								.permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/api/temp/**"))
								.permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**"))
								.permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/oauth2/**"))
								.permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/assign/**"))
								.hasRole(RoleType.NONE.name())
								.requestMatchers(AntPathRequestMatcher.antMatcher("/api/**"))
								.authenticated()
				)
				.oauth2Login(oauth2Login ->
						oauth2Login
								.userInfoEndpoint(userInfoEndpoint ->
										userInfoEndpoint.userService(customOAuth2UserService)
								)
								.successHandler(OAuth2LoginSuccessHandler)
								.failureHandler(OAuth2LoginFailureHandler)
				)
				.headers(headersConfigurer ->
						headersConfigurer
								.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
				)
				.addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter.class);
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
				.ignoring()
				.requestMatchers(
						PathRequest.toStaticResources().atCommonLocations()
				);
	}

	@Bean
	public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
		return new JwtAuthenticationProcessingFilter(
				memberReadRepository,
				jwtProperties,
				jwtService,
				redisUtil
		);
	}
}
