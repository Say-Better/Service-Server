package com.saybetter.domain.member.domain;

import com.saybetter.global.common.constant.Provider;
import com.saybetter.global.common.constant.RoleType;
import com.saybetter.global.common.constant.Status;
import com.saybetter.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private Status status;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "birth_date", nullable = false)
	private String birthDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private RoleType role;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider", nullable = false, length = 20)
	private Provider provider;

	@Column(name = "provider_id", nullable = false)
	private String providerId;

	@Column(name = "login_id", nullable = false)
	private String loginId;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Builder
	private Member(
			final String email,
			final String birthDate,
			final RoleType role,
			final Provider provider,
			final String providerId,
			final String loginId,
			final String name
	) {
		this.status = Status.ACTIVE;
		this.email = email;
		this.birthDate = birthDate;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.loginId = loginId;
		this.name = name;
	}

	public static Member createMember(
			final String email,
			final String birthDate,
			final RoleType role,
			final Provider provider,
			final String providerId,
			final String loginId,
			final String name
	) {
		return new Member(email, birthDate, role, provider, providerId, loginId, name);
	}

	public void assignRole(RoleType role) {
		this.role = role;
	}
}
