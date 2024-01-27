package com.aesp.global.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize ->
						authorize
								.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).authenticated()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/temp/**")).authenticated()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**")).authenticated()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).authenticated()
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
