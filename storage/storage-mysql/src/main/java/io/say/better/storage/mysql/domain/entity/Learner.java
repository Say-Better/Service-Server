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
@Entity(name = "LEARNER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Learner extends Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "learner_id")
	private Long learnerId;

	@Builder
	private Learner(
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
        this.role = RoleType.LEARNER;
		this.provider = provider;
		this.providerId = providerId;
		this.loginId = loginId;
		this.name = name;
	}

	public static Learner createLearner(
			final String email,
			final String birthDate,
			final Provider provider,
			final String providerId,
			final String loginId,
			final String name
	) {
		return new Learner(email, birthDate, provider, providerId, loginId, name);
	}
}
