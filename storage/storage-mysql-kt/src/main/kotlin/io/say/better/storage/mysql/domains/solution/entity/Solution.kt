package io.say.better.storage.mysql.domains.solution.entity

import io.say.better.core.common.constant.Status
import io.say.better.core.common.constant.Status.ACTIVE
import io.say.better.storage.mysql.common.model.BaseTimeEntity
import io.say.better.storage.mysql.domains.account.entity.Educator
import io.say.better.storage.mysql.domains.account.entity.Learner
import io.say.better.storage.mysql.domains.solution.type.ProgressState
import io.say.better.storage.mysql.domains.solution.type.ProgressState.HAVE_TO_REVIEW
import io.say.better.storage.mysql.domains.solution.type.ProgressState.PROCESSING
import io.say.better.storage.mysql.domains.solution.type.ProgressState.TERMINATED
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity(name = "SOLUTION")
class Solution(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "solution_id")
    private val solutionId: Long = 0,
    @Enumerated(STRING)
    @Column(name = "now_state", nullable = false)
    private var nowState: ProgressState,
    @Column(name = "educationGoal", nullable = false) // length 255
    private val educationGoal: String,
    @Column(name = "description", nullable = false) // length 255
    private val description: String,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private val writer: Educator,
    @OneToOne(fetch = LAZY)
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
    @Enumerated(STRING)
    @Column(name = "status", nullable = false, length = 20)
    private var status: Status = ACTIVE,
) : BaseTimeEntity() {
    fun onActivated() {
        this.status = ACTIVE
    }

    fun onStart() {
        this.nowState = PROCESSING
    }

    fun onEnd() {
        this.nowState = HAVE_TO_REVIEW
    }

    fun onReviewEnd() {
        this.nowState = TERMINATED
    }
}
