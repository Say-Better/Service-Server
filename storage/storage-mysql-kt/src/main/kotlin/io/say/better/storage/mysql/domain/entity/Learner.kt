package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "LEARNER")
class Learner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learner_id")
    var learnerId: Long = 0,
    @ManyToOne(targetEntity = Member::class)
    @JoinColumn(name = "member_id")
    val memberId: Member,
    @Column(name = "name", nullable = false, length = 100)
    val name: String? = null,
    @Column(name = "birth_date")
    val birthDate: String = "",
) : BaseTimeEntity() {
    companion object {
        fun createLearner(
            memberId: Member,
            name: String? = null,
            birthDate: String = "",
        ): Learner {
            return Learner(
                memberId = memberId,
                name = name,
                birthDate = birthDate,
            )
        }
    }
}
