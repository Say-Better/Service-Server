package com.saybetter.global.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.saybetter.global.config.web.CorsConfig;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsConfig;

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
								.requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**")).permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/temp/**")).permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**")).authenticated()
				)
				.headers(headersConfigurer ->
						headersConfigurer
								.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
				);
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
}
