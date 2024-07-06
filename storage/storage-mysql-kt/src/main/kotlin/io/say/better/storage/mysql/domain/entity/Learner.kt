package io.say.better.storage.mysql.domain.entity

import io.say.better.storage.mysql.domain.constant.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

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
    var birthDate: String = "",
    @Column(name = "age")
    var age: Int = 0,
    @Column(name = "gender")
    var gender: Gender = Gender.ETC,
    @Column(name = "img_url")
    var imgUrl: String = "",
) : BaseTimeEntity() {
    fun initializeLearnerInfo(
        url: String,
        name: String,
        birthDate: String,
        gender: String,
    ) {
        memberId.name = name
        imgUrl = url
        this.birthDate = birthDate
        if (gender == "M") {
            this.gender = Gender.MALE
        } else {
            this.gender = Gender.FEMALE
        }
        this.age = getAgeFromBirthday(birthDate)
    }

    private fun getAgeFromBirthday(birthDate: String): Int {
        val year = birthDate.split(".")[0].toInt()
        val month = birthDate.split(".")[1].toInt()
        val date = birthDate.split(".")[2].toInt()

        val now = LocalDate.now()

        var age = now.year - year

        if (month <= now.monthValue) { // 생일의 달이 현재보다 작거나 같을때
            if (month == now.monthValue) { // 생일의 달이 현재와 같을때
                if (date > now.dayOfMonth) { // 생일의 일자가 현재보다 더 크면 아직 생일이 지나지 않음
                    age -= 1
                }
            } else { // 생일의 달이 현재보다 작다면
                age -= 1 // 일자와 상관없이 생일이 지나지 않음
            }
        }

        return age
    }

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
