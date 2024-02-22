package com.saybetter.global.common.constant;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.saybetter.global.auth.exception.AuthException;
import com.saybetter.global.common.code.status.ErrorStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
	GOOGLE("google"),
	NAVER("naver"),
	KAKAO("kakao"),
	FACEBOOK("facebook");

	private final String description;

	private static final Map<String, Provider> descriptions =
			Collections.unmodifiableMap(
					Stream.of(values())
							.collect(
									Collectors.toMap(
											Provider::getDescription,
											Function.identity()
									)
							));

	public static Provider find(String description) {
		return Optional
				.ofNullable(descriptions.get(description))
				.orElseThrow(() -> new AuthException(ErrorStatus.INTERNAL_SERVER_ERROR));
	}
}
