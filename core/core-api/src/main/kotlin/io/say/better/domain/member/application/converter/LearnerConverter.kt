package io.say.better.domain.member.application.converter

import io.say.better.core.enums.Provider
import io.say.better.storage.mysql.domain.entity.Learner

class LearnerConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toLearner(
            email: String,
            birthDate: String?,
            provider: Provider,
            providerId: String,
            loginId: String,
            name: String
        ): Learner {
            return Learner.createLearner(email, birthDate, provider, providerId, loginId, name)
        }
    }
}
