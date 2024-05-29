package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Learner
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LearnerReadRepository : JpaRepository<Learner, Long> {
    fun findByLearnerId(learnerId: Long): Optional<Learner>
    fun findByMemberId(learnerMember: Member): Optional<Learner>
}
