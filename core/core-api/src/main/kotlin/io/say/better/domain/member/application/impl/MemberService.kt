package io.say.better.domain.member.application.impl

import io.say.better.core.enums.RoleType
import io.say.better.domain.member.exception.MemberException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.utils.SecurityUtil
import io.say.better.storage.mysql.dao.repository.*
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val securityUtil: SecurityUtil,
    private val educatorReadRepository: EducatorReadRepository,
    private val educatorWriteRepository: EducatorWriteRepository,
    private val learnerReadRepository: LearnerReadRepository,
    private val learnerWriteRepository: LearnerWriteRepository
) {

    fun currentMember(): Member {
        val email = securityUtil.currentUserEmail
        return getMember(email)
    }

    fun getMember(email: String): Member {
        val isValidEducator = findByEmail(educatorReadRepository, email)
        val isValidLearner = findByEmail(learnerReadRepository, email)

        if (isValidEducator) {
            return educatorReadRepository.findByEmail(email).get()
        } else if (isValidLearner) {
            return learnerReadRepository.findByEmail(email).get()
        } else {
            throw MemberException(ErrorStatus.MEMBER_NOT_FOUND)
        }
    }

    private fun <T> findByEmail(repository: MemberReadRepository<T>, email: String): Boolean {
        return repository.findByEmail(email).isPresent
    }

    fun assignUserRole(userEmail: String, role: RoleType) {
        val member = getMember(userEmail)

        if (member.role != RoleType.NONE) {
            throw MemberException(ErrorStatus.MEMBER_HAVE_ROLE_SIGN)
        }
    }
}
