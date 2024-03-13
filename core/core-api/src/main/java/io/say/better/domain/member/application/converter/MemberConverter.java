package io.say.better.domain.member.application.converter;

import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.core.enums.Provider;
import io.say.better.core.enums.RoleType;

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
