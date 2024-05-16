package io.say.better.storage.mysql.domain.entity;

import io.say.better.core.enums.Status;
import io.say.better.storage.mysql.domain.constant.ProgressState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@Entity(name = "SOLUTION")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Solution extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id")
    private Long solutionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "now_state", nullable = false)
    private ProgressState nowState;

    @Column(name = "educationGoal", nullable = false) // length 255
    private String educationGoal;

    @Column(name = "description", nullable = false) // length 255
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Educator writer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id")
    private Learner learner;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    // 회기당 의사소통 기회 부여 횟수
    @Column(name = "comm_opt_times", nullable = false, columnDefinition = "int default 0")
    private Integer commOptTimes;

    // 의사소통 기회 부여 시간
    @Column(name = "comm_opt_cnt", nullable = false, columnDefinition = "int default 0")
    private Integer commOptCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;

    public void onActivated() {
        this.status = Status.ACTIVE;
    }
}
