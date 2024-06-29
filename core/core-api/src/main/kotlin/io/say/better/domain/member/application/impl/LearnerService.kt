package io.say.better.domain.member.application.impl

import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.domain.member.exception.MemberException
import io.say.better.storage.mysql.dao.repository.LearnerReadRepository
import io.say.better.storage.mysql.dao.repository.LearnerWriteRepository
import io.say.better.storage.mysql.domain.entity.Learner
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.stereotype.Service

@Service
class LearnerService(
    private val learnerReadRepository: LearnerReadRepository,
    private val learnerWriteRepository: LearnerWriteRepository,
) {
    fun getLearner(learnerMember: Member): Learner =
        learnerReadRepository.findByMemberId(learnerMember).orElse(null)
            ?: Learner.createLearner(learnerMember)

    fun getLearnerByMember(learnerMember: Member): Learner =
        learnerReadRepository
            .findByMemberId(learnerMember)
            .orElseThrow { MemberException(ErrorStatus.LEARNER_NOT_FOUND) }

    fun postLearnerInfo(
        member: Member,
        url: String,
        name: String,
    ) {
        val learner = getLearner(member)

        learner.initializeLearnerInfo(url, name)
    }
}
