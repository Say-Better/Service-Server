package com.saybetter.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ConfigurationProperties("jwt")
@ConfigurationPropertiesBinding
public class JwtProperties {

	private String bearer;
	private String secret;
	private String accessHeader;
	private Long accessExpiration;
	private Long refreshExpiration;
	private String refreshHeader;

}
