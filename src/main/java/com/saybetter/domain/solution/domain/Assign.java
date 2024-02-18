package com.saybetter.domain.solution.domain;

import com.saybetter.domain.member.domain.Member;
import com.saybetter.domain.solution.domain.constant.AssignState;
import com.saybetter.domain.solution.domain.constant.AssignStep;
import com.saybetter.global.common.constant.Status;
import com.saybetter.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity(name = "ASSIGN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assign extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "assign_id")
	private Long assignId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solution_id", nullable = false)
	private Solution solution;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_id", nullable = false)
	private Member learner;

	// 현재 단계
	@Enumerated(EnumType.STRING)
	@Column(name = "now_step", nullable = false, length = 20)
	private AssignStep nowAssignStep;

	// 현재 회기
	@Column(name = "now_session", nullable = false, columnDefinition = "int default 0")
	private Integer nowSession;

	// 현재 회기 상태
	@Enumerated(EnumType.STRING)
	@Column(name = "now_state", nullable = false, length = 20)
	private AssignState nowState;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private Status status;

}
