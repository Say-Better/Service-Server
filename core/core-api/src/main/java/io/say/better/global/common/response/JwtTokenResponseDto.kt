package io.say.better.global.common.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class JwtTokenResponseDto {

	private String accessToken;
	private String refreshToken;
	private LocalDateTime expiredTime;
	@JsonProperty("isExisted")
	private boolean isExisted;

}
