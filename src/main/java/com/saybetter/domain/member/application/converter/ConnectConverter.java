package com.saybetter.domain.member.application.converter;

import com.saybetter.domain.member.domain.Connect;
import com.saybetter.domain.member.domain.Member;

public class ConnectConverter {

	public static Connect toConnect(Member educator, Member learner) {
		return Connect.builder()
				.educator(educator)
				.learner(learner)
				.build();
	}

}
