package io.say.better.domain.member.application.converter

import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.storage.mysql.domains.account.entity.Member

class MemberConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toMember(
            email: String,
            birthDate: String,
            role: RoleType,
            provider: Provider,
            providerId: String,
            loginId: String,
            name: String,
        ): Member =
            Member.createMember(
                email,
                birthDate,
                role,
                provider,
                providerId,
                loginId,
                name,
            )
    }
}
