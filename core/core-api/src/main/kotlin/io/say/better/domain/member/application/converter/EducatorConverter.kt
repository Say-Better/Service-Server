package io.say.better.domain.member.application.converter

import io.say.better.core.enums.Provider
import io.say.better.storage.mysql.domain.entity.Educator

class EducatorConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toEducator(
                email: String?,
                birthDate: String?,
                provider: Provider?,
                providerId: String?,
                loginId: String?,
                name: String?
        ): Educator {
            return Educator.createEducator(email, birthDate, provider, providerId, loginId, name)
        }
    }
}
