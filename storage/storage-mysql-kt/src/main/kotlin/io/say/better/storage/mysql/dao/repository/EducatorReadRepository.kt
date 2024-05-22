package io.say.better.storage.mysql.dao.repository

import io.say.better.core.enums.Provider
import io.say.better.storage.mysql.domain.entity.Educator
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface EducatorReadRepository : JpaRepository<Educator, Long>, MemberReadRepository<Educator> {
    override fun findByProviderAndLoginId(
        provider: Provider,
        loginId: String,
    ): Optional<Educator>

    override fun findByEmail(email: String): Optional<Educator>
}
