package io.say.better.storage.mysql.dao.repository

import io.say.better.core.enums.Provider
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberReadRepository : JpaRepository<Member, Long> {
    fun findByProviderAndLoginId(
        provider: Provider,
        loginId: String,
    ): Optional<Member>

    fun findByEmail(email: String): Optional<Member>
}
