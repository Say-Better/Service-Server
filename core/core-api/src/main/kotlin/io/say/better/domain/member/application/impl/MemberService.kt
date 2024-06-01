package io.say.better.domain.member.application.impl

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.core.enums.auth.info.OAuth2UserInfo
import io.say.better.domain.member.exception.MemberException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.utils.SecurityUtil
import io.say.better.storage.mysql.dao.repository.EducatorReadRepository
import io.say.better.storage.mysql.dao.repository.EducatorWriteRepository
import io.say.better.storage.mysql.dao.repository.LearnerReadRepository
import io.say.better.storage.mysql.dao.repository.LearnerWriteRepository
import io.say.better.storage.mysql.dao.repository.MemberReadRepository
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val securityUtil: SecurityUtil,
    private val educatorReadRepository: EducatorReadRepository,
    private val educatorWriteRepository: EducatorWriteRepository,
    private val learnerReadRepository: LearnerReadRepository,
    private val learnerWriteRepository: LearnerWriteRepository,
    private val memberReadRepository: MemberReadRepository,
    private val memberWriteRepository: MemberWriteRepository,
) {
    fun currentMember(): Member {
        val email = securityUtil.currentUserEmail
        return getMember(email)
    }

    fun getMember(email: String): Member {
        return memberReadRepository.findByEmail(email)
            .orElseThrow { MemberException(ErrorStatus.MEMBER_NOT_FOUND) }
    }

    fun assignUserRole(
        userEmail: String,
        role: RoleType,
    ) {
        val member = getMember(userEmail)

        if (member.role != RoleType.NONE) {
            throw MemberException(ErrorStatus.MEMBER_HAVE_ROLE_SIGN)
        }
    }

    fun getEducator(educatorMember: Member): Educator {
        return educatorReadRepository.findByMemberId(educatorMember)
            .orElseThrow { MemberException(ErrorStatus.EDUCATOR_NOT_FOUND) }
    }

    fun getLearner(learnerMember: Member): Learner {
        return learnerReadRepository.findByMemberId(learnerMember)
            .orElseThrow { MemberException(ErrorStatus.LEARNER_NOT_FOUND) }
    }

    fun getMember(provider: Provider, userInfo: OAuth2UserInfo): Member {
        val loginId = getLoginId(provider, userInfo)
        val findUser = memberReadRepository.findByProviderAndLoginId(provider, loginId).orElse(null)
            ?: return saveMember(userInfo, provider)

        return findUser
    }

    private fun getLoginId(provider: Provider, userInfo: OAuth2UserInfo): String {
        val providerId = userInfo.providerId
        return "$provider-$providerId"
    }

    private fun saveMember(attributes: OAuth2UserInfo, provider: Provider): Member {
        val createdUser = Member.createMember(
            email = attributes.email,
            birthDate = "",
            role = RoleType.NONE,
            provider = provider,
            providerId = attributes.providerId,
            loginId = getLoginId(provider, attributes),
            name = attributes.name)

        return memberWriteRepository.save(createdUser)
    }
}
