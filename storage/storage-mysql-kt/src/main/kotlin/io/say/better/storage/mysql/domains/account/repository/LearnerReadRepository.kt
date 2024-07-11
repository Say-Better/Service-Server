package io.say.better.storage.mysql.domains.account.repository

import io.say.better.storage.mysql.domains.account.entity.Learner
import io.say.better.storage.mysql.domains.account.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LearnerReadRepository : JpaRepository<Learner, Long> {
    fun findByLearnerId(learnerId: Long): Optional<Learner>

    fun findByMemberId(learnerMember: Member): Optional<Learner>
}
