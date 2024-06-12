package io.say.better.domain.member.application.impl

import io.say.better.storage.mysql.dao.repository.LearnerReadRepository
import io.say.better.storage.mysql.dao.repository.LearnerWriteRepository
import io.say.better.storage.mysql.domain.entity.Learner
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.stereotype.Service

@Service
class LeanerService(
    private val learnerReadRepository: LearnerReadRepository,
    private val learnerWriteRepository: LearnerWriteRepository,
) {
    fun getLearner(learnerMember: Member): Learner =
        learnerReadRepository.findByMemberId(learnerMember).orElse(null)
            ?: Learner.createLearner(learnerMember)
}
