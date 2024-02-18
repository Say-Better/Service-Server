package com.saybetter.domain.solution.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssignStep {
	BASELINE("기초선"),
	INTERVENTION("중재"),
	MAINTENANCE("유지");

	private final String description;
}
