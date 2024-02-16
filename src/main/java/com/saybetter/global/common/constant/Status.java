package com.saybetter.global.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	ACTIVE("활성"),
	INACTIVE("비활성"),
	DELETED("삭제 대기");

	private final String description;
}
