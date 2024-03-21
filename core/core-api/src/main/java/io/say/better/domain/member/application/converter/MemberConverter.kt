package io.say.better.domain.member.application.converter

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.storage.mysql.domain.entity.Member

class MemberConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toMember(
                email: String?,
                birthDate: String?,
                role: RoleType?,
                provider: Provider?,
                providerId: String?,
                loginId: String?,
                name: String?
        ): Member {
            return Member.createMember(email, birthDate, role, provider, providerId, loginId, name)
        }
    }
}
