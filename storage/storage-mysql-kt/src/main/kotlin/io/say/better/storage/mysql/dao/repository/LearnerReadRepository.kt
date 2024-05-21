package io.say.better.storage.mysql.dao.repository

import io.say.better.core.enums.Provider
import io.say.better.storage.mysql.domain.entity.Learner
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LearnerReadRepository : JpaRepository<Learner, Long>, MemberReadRepository<Learner> {
    override fun findByProviderAndLoginId(
        provider: Provider,
        loginId: String,
    ): Optional<Learner>

    override fun findByEmail(email: String): Optional<Learner>
}
