package io.say.better.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties(prefix = "jwt")
@ConfigurationPropertiesBinding
public record JwtProperties(
		String bearer,
		String secret,
		String accessHeader,
		Long accessExpiration,
		Long refreshExpiration,
		String refreshHeader
) {

}
