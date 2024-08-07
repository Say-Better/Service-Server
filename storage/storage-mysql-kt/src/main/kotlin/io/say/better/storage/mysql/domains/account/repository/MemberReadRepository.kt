package io.say.better.storage.mysql.domains.account.repository

import io.say.better.core.common.constant.Provider
import io.say.better.storage.mysql.domains.account.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberReadRepository : JpaRepository<Member, Long> {
    fun findByProviderAndLoginId(
        provider: Provider,
        loginId: String,
    ): Optional<Member>

    fun findByEmail(email: String): Optional<Member>
}
