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
}
