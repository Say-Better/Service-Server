package io.say.better.storage.mysql.domain.entity;

import io.say.better.core.common.Provider;
import io.say.better.core.common.RoleType;
import io.say.better.core.common.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "EDUCATOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Educator extends Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "educator_id")
	private Long educatorId;

	@Builder
	private Educator(
			final String email,
			final String birthDate,
			final Provider provider,
			final String providerId,
			final String loginId,
			final String name
	) {
		this.status = Status.ACTIVE;
		this.email = email;
		this.birthDate = birthDate;
		this.role = RoleType.EDUCATOR;
		this.provider = provider;
		this.providerId = providerId;
		this.loginId = loginId;
		this.name = name;
	}

	public static Educator createEducator(
			final String email,
			final String birthDate,
			final Provider provider,
			final String providerId,
			final String loginId,
			final String name
	) {
		return new Educator(email, birthDate, provider, providerId, loginId, name);
	}
}
