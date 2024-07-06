package io.say.better.domain.member.ui.dto

class MemberRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class LearnerInitialInfoDTO(
        val name: String,
        val birthday: String,
        val gender: String,
    )
}
