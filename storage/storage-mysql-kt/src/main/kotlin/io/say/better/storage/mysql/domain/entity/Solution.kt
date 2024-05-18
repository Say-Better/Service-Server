package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Status
import io.say.better.storage.mysql.domain.constant.ProgressState
import jakarta.persistence.*


@Entity(name = "SOLUTION")
class Solution(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "solution_id")
        private val solutionId: Long = 0,

        @Enumerated(EnumType.STRING)
        @Column(name = "now_state", nullable = false)
        private var nowState: ProgressState,

        @Column(name = "educationGoal", nullable = false) // length 255
        private val educationGoal: String,

        @Column(name = "description", nullable = false) // length 255
        private val description: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "writer_id", nullable = false)
        private val writer: Educator,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "learner_id")
        private val learner: Learner,

        @Column(name = "title", nullable = false, length = 100)
        private val title: String,

        // 회기당 의사소통 기회 부여 횟수
        @Column(name = "comm_opt_times", nullable = false, columnDefinition = "int default 0")
        private val commOptTimes: Int,

        // 의사소통 기회 부여 시간
        @Column(name = "comm_opt_cnt", nullable = false, columnDefinition = "int default 0")
        private val commOptCnt: Int,

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private var status: Status = Status.ACTIVE
) : BaseTimeEntity() {
    fun onActivated() {
        this.status = Status.ACTIVE
    }

    fun onStart() {
        this.nowState = ProgressState.PROCESSING
    }

    fun onEnd() {
        this.nowState = ProgressState.HAVE_TO_REVIEW
    }

    fun onReviewEnd() {
        this.nowState = ProgressState.TERMINATED
    }
}
