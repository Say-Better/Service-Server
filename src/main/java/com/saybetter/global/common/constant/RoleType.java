package com.saybetter.global.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
	EDUCATOR("교육자"),
	LEARNER("학습자"),
	ADMIN("관리자");

	private final String description;
}
