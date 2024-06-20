package io.say.better.storage.mysql.domain.entity

import io.say.better.storage.mysql.domain.constant.Gender
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
    val name: String = "",
    @Column(name = "birth_date")
    val birthDate: String = "",
    @Column(name = "age")
    val age: Int = 0,
    @Column(name = "gender")
    val gender: Gender = Gender.ETC,
    @Column(name = "img_url")
    val imgUrl: String = ""
) : BaseTimeEntity() {
    companion object {
        fun createLearner(
            memberId: Member,
            name: String = "",
            birthDate: String = "",
            age: Int = 0,
            gender: Gender = Gender.ETC,
            imgUrl: String = "",
        ): Learner =
            Learner(
                memberId = memberId,
                name = name,
                birthDate = birthDate,
                age = age,
                gender = gender,
                imgUrl = imgUrl,
            )
    }
}
