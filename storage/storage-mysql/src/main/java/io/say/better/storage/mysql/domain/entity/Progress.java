package io.say.better.storage.mysql.domain.entity;

import io.say.better.core.common.Status;
import io.say.better.storage.mysql.domain.constant.AssignStep;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "ASSIGN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "progress_id")
	private Long progressId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solution_id", nullable = false)
	private Solution solution;

	// 현재 단계
	@Enumerated(EnumType.STRING)
	@Column(name = "now_step", nullable = false, length = 20)
	private AssignStep nowAssignStep;

	// 현재 회기
	@Column(name = "session_order", nullable = false)
	private Integer sessionOrder;

    // 세션 목표
    @Column(name = "session_goal", nullable = false) // length 255
    private String sessionGoal;

    // 세션 설명
    @Column(name = "session_description", nullable = false, columnDefinition = "") // length 255
    private String sessionDescription;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private Status status;

    @Builder
    public Progress( Solution solution, AssignStep nowAssignStep, Integer sessionOrder, String sessionGoal, String sessionDescription) {
        this.solution = solution;
        this.nowAssignStep = nowAssignStep;
        this.sessionOrder = sessionOrder;
        this.sessionGoal = sessionGoal;
        this.sessionDescription = sessionDescription;
    }

    public void onActivated() {
        status = Status.ACTIVE;
    }
}
