package io.say.better.storage.mysql.domain.entity;

import io.say.better.core.common.Provider;
import io.say.better.core.common.RoleType;
import io.say.better.core.common.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Member extends BaseTimeEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	Status status;

	@Column(name = "email", nullable = false, length = 100)
	String email;

	@Column(name = "birth_date")
	String birthDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	RoleType role;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider", nullable = false, length = 20)
	Provider provider;

	@Column(name = "provider_id", nullable = false)
	String providerId;

	@Column(name = "login_id", nullable = false)
	String loginId;

	@Column(name = "name", nullable = false, length = 100)
	String name;
}
