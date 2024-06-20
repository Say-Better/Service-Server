package io.say.better.domain.member.ui.dto

import io.say.better.storage.mysql.domain.constant.Gender

class MemberResponse private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class EducatorDTO(
        val name: String,
        val imgUrl: String,
    )

    data class LearnerDTO(
        val name: String,
        val age: Int,
        val gender: Gender,
        val imgUrl: String,
    )

    companion object {
        fun createEducatorDTO(
            name: String,
            imgUrl: String,
        ): EducatorDTO = EducatorDTO(name, imgUrl)

        fun createLearnerDTO(
            name: String,
            age: Int,
            gender: Gender,
            imgUrl: String,
        ): LearnerDTO = LearnerDTO(name, age, gender, imgUrl)
    }
}
