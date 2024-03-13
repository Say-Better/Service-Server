package io.say.better.storage.mysql.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReactionType {
	TURE("정반응"),
	FALSE("오반응"),
	NONE("없음");

	private final String description;
}
