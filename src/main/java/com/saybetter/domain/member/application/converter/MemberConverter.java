package com.saybetter.domain.member.application.converter;

import com.saybetter.domain.member.domain.Member;
import com.saybetter.global.common.constant.Provider;
import com.saybetter.global.common.constant.RoleType;

public class MemberConverter {

	private MemberConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static Member toMember(
			final String email,
			final String birthDate,
			final RoleType role,
			final Provider provider,
			final String providerId,
			final String loginId,
			final String name
	) {
		return Member.createMember(email, birthDate, role, provider, providerId, loginId, name);
	}

}
