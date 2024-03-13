package io.say.better.domain.member.application.converter;

import io.say.better.storage.mysql.domain.entity.Connect;
import io.say.better.storage.mysql.domain.entity.Member;

public class ConnectConverter {

	private ConnectConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static Connect toConnect(Member educator, Member learner) {
		return Connect.builder()
				.educator(educator)
				.learner(learner)
				.build();
	}

}
