package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Status
import io.say.better.storage.mysql.domain.constant.AssignStep
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "PROGRESS")
class Progress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    var progressId: Long = 0,
    @JoinColumn(name = "solution_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val solution: Solution,
    @Column(name = "now_step", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private var nowAssignStep: AssignStep,
    @Column(name = "session_order", nullable = false)
    private var sessionOrder: Int,
    @Column(name = "session_goal")
    private var sessionGoal: String?,
    @Column(name = "session_description", columnDefinition = "")
    private var sessionDescription: String?,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private var status: Status = Status.ACTIVE,
) : BaseTimeEntity() {
    fun onActivated() {
        status = Status.ACTIVE
    }
}
