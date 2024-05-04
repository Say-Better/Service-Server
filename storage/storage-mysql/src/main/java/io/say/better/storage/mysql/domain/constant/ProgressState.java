package io.say.better.storage.mysql.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgressState {
	READY("수업 가능"),
	PROCESSING("수업 진행"),
	HAVE_TO_REVIEW("수업 리뷰"),
	TERMINATED("수업 종료");

	private final String description;
}
