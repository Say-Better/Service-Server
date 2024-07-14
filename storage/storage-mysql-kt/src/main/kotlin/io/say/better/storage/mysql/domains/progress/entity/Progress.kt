package io.say.better.storage.mysql.domains.progress.entity

import io.say.better.core.common.constant.Status
import io.say.better.core.common.constant.Status.ACTIVE
import io.say.better.storage.mysql.common.model.BaseTimeEntity
import io.say.better.storage.mysql.domains.progress.type.AssignStep
import io.say.better.storage.mysql.domains.solution.entity.Solution
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

@Entity(name = "PROGRESS")
class Progress(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "progress_id")
    var progressId: Long = 0,
    @JoinColumn(name = "solution_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    val solution: Solution,
    @Column(name = "now_step", nullable = false, length = 20)
    @Enumerated(STRING)
    private var nowAssignStep: AssignStep,
    @Column(name = "session_order", nullable = false)
    private var sessionOrder: Int,
    @Column(name = "session_goal")
    private var sessionGoal: String?,
    @Column(name = "session_description", columnDefinition = "")
    private var sessionDescription: String?,
    @Enumerated(STRING)
    @Column(name = "status", nullable = false, length = 20)
    private var status: Status = ACTIVE,
) : BaseTimeEntity() {
    fun onActivated() {
        status = ACTIVE
    }
}
