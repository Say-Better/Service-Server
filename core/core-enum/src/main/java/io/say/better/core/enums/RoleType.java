package io.say.better.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
	EDUCATOR("교육자"),
	LEARNER("학습자"),
	ADMIN("관리자"),
	NONE("초기 상태");

	private final String description;
}