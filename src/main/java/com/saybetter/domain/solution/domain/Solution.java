package com.saybetter.domain.solution.domain;

import com.saybetter.domain.member.domain.Member;
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
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "SOLUTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Solution extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "solution_id")
	private Long solutionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id", nullable = false)
	private Member writer;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "learner_link_cnt", nullable = false, columnDefinition = "int default 0")
	private Integer learnerLinkCnt;

	// 기초선 회기 수
	@Size(max = 10)
	@Column(name = "baseline_session_time", nullable = false, columnDefinition = "int default 0")
	private Integer baselineSessionTime;

	// 중재 완료하기 위한 장반응률 임계값
	@Size(max = 1)
	@Column(name = "inter_complete_thresh", nullable = false, columnDefinition = "double default 0.0")
	private Double interCompleteThresh;

	// 중재 ~ 유지 사이의 기간
	@Column(name = "inter_maintain_term", nullable = false, columnDefinition = "int default 0")
	private Integer interMaintainTerm;

	// 유지 회기 수
	@Column(name = "maintain_session_time", nullable = false, columnDefinition = "int default 0")
	private Integer maintainSessionTime;

	// 회기당 의사소통 기회 부여 횟수
	@Column(name = "comm_opt_times", nullable = false, columnDefinition = "int default 0")
	private Integer commOptTimes;

	// 의사소통 기회 부여 시간
	@Column(name = "comm_opt_cnt", nullable = false, columnDefinition = "int default 0")
	private Integer commOptCnt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private Status status;

	@Builder
	public Solution(
			Member writer,
			String title,
			Integer learnerLinkCnt,
			Integer baselineSessionTime,
			Double interCompleteThresh,
			Integer interMaintainTerm,
			Integer commOptTimes,
			Integer commOptCnt
	) {
		this.writer = writer;
		this.title = title;
		this.learnerLinkCnt = learnerLinkCnt;
		this.baselineSessionTime = baselineSessionTime;
		this.interCompleteThresh = interCompleteThresh;
		this.interMaintainTerm = interMaintainTerm;
		this.maintainSessionTime = baselineSessionTime; // TODO: 관련 내용이 없어서 임시로 추가
		this.commOptTimes = commOptTimes;
		this.commOptCnt = commOptCnt;
		this.status = Status.INACTIVE;
	}

	public void onActivated() {
		this.status = Status.ACTIVE;
	}
}
