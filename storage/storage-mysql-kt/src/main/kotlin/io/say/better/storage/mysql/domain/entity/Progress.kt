package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Status
import io.say.better.storage.mysql.domain.constant.AssignStep
import jakarta.persistence.*

@Entity(name = "PROGRESS")
class Progress(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "progress_id") var progressId: Long = 0,

        @JoinColumn(name = "solution_id", nullable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private val solution: Solution, // 현재 단계

        @Column(name = "now_step", nullable = false, length = 20)
        @Enumerated(EnumType.STRING)
        private var nowAssignStep: AssignStep, // 현재 회기

        @Column(name = "session_order", nullable = false)
        private var sessionOrder: Int,// length 255 // 세션 목표

        @Column(name = "session_goal")
        private var sessionGoal: String?,// length 255 // 세션 설명

        @Column(name = "session_description", columnDefinition = "")
        private var sessionDescription: String?,

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private var status: Status = Status.ACTIVE

) : BaseTimeEntity() {
    fun onActivated() {
        status = Status.ACTIVE
    }
}
