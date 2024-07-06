package io.say.better.domain.member.application.impl

import io.say.better.core.common.auth.info.OAuth2UserInfo
import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.core.common.constant.AppType
import io.say.better.core.common.constant.AppType.EDUCATOR
import io.say.better.core.common.constant.AppType.LEARNER
import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.domain.member.application.helper.CommonLoginIdHelper
import io.say.better.domain.member.application.helper.EmailEncoderHelper
import io.say.better.domain.member.exception.MemberException
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.global.utils.SecurityUtil
import io.say.better.storage.mysql.dao.repository.MemberReadRepository
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val securityUtil: SecurityUtil,
    private val commonLoginIdHelper: CommonLoginIdHelper,
    private val emailEncoderHelper: EmailEncoderHelper,
    private val memberReadRepository: MemberReadRepository,
    private val memberWriteRepository: MemberWriteRepository,
) {
    fun currentMember(): Member {
        val email = securityUtil.currentUserEmail
        return getMemberByEmail(email)
    }

    fun getMemberByEmail(email: String): Member =
        memberReadRepository
            .findByEmail(email)
            .orElseThrow { MemberException(ErrorStatus.MEMBER_NOT_FOUND) }

    fun assignUserRole(
        userEmail: String,
        role: RoleType,
    ) {
        val member = getMemberByEmail(userEmail)

        if (member.role != RoleType.NONE) {
            throw MemberException(ErrorStatus.MEMBER_HAVE_ROLE_SIGN)
        }
    }

    fun getMemberByEmail(
        appType: AppType,
        provider: Provider,
        userInfo: OAuth2UserInfo,
    ): Member {
        val loginId = getLoginId(provider, userInfo)
        val roleType = getRoleType(appType)
        val findUser = memberReadRepository.findByProviderAndLoginId(provider, loginId).orElse(null)

        val responseUser =
            if (findUser == null) {
                createMember(roleType, provider, userInfo)
            } else {
                updateRole(findUser, roleType)
            }

        return responseUser
    }

    fun getMemberByEmail(
        appType: AppType,
        provider: Provider,
        request: AuthRequest.CommonLoginDTO,
    ): Member {
        val loginId = commonLoginIdHelper.getLoginId(request.email)
        val roleType = getRoleType(appType)
        val findUser = memberReadRepository.findByProviderAndLoginId(provider, loginId).orElse(null)

        val responseUser =
            if (findUser == null) {
                createMember(roleType, provider, loginId, request)
            } else {
                updateRole(findUser, roleType)
            }

        return responseUser
    }

    private fun getRoleType(appType: AppType) =
        when (appType) {
            EDUCATOR -> RoleType.EDUCATOR
            LEARNER -> RoleType.LEARNER
        }

    private fun updateRole(
        member: Member,
        role: RoleType,
    ): Member {
        if (member.role != role) {
            member.onEducatorLearner()
        }
        return memberWriteRepository.save(member)
    }

    private fun getLoginId(
        provider: Provider,
        userInfo: OAuth2UserInfo,
    ): String {
        val providerId = userInfo.providerId
        return "$provider-$providerId"
    }

    private fun createMember(
        role: RoleType,
        provider: Provider,
        attributes: OAuth2UserInfo,
    ): Member {
        val createdUser =
            Member.createMember(
                email = attributes.email,
                birthDate = "",
                role = role,
                provider = provider,
                providerId = attributes.providerId,
                loginId = getLoginId(provider, attributes),
                name = attributes.name,
            )

        return memberWriteRepository.save(createdUser)
    }

    private fun createMember(
        role: RoleType,
        provider: Provider,
        loginId: String,
        attributes: AuthRequest.CommonLoginDTO,
    ): Member {
        val createdUser =
            Member.createMember(
                email = attributes.email,
                birthDate = "",
                role = role,
                provider = provider,
                providerId = emailEncoderHelper.encoder(attributes.email),
                loginId = loginId,
                name = attributes.name,
            )

        return memberWriteRepository.save(createdUser)
    }
}
