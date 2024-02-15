package com.saybetter.domain.member.domain;

import com.saybetter.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "CONNECT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Connect extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "connect_id")
	private Long connectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "educator_id")
	private Member educator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_id")
	private Member learner;
	
}
