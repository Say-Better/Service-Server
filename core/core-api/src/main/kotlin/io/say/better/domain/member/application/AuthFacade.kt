package io.say.better.domain.member.application

import io.say.better.client.oauth.strategy.OauthSocialLogin
import io.say.better.core.common.constant.AppType
import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.domain.member.application.converter.AuthResponseConverter
import io.say.better.domain.member.application.impl.EducatorService
import io.say.better.domain.member.application.impl.LearnerService
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.domain.member.ui.dto.AuthResponse
import io.say.better.global.jwt.service.JwtService
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val memberService: MemberService,
    private val educatorService: EducatorService,
    private val learnerService: LearnerService,
    private val jwtService: JwtService,
    oauthSocialLogins: List<OauthSocialLogin>,
) {
    private val socialLoginStrategyMap = oauthSocialLogins.associateBy { it.provider }

    fun assignUserRole(role: RoleType) {
        val member = memberService.currentMember()
        memberService.assignUserRole(member.email, role)
    }

    fun login(
        appType: AppType,
        provider: Provider,
        request: AuthRequest.LoginDTO,
    ): AuthResponse.LoginDTO {
        val userInfo = socialLoginStrategyMap[provider]!!.verifyToken(request.identityToken)
        val member = memberService.getMemberByEmail(appType, provider, userInfo)
        val token = jwtService.createServiceToken(member)

        val needMemberInfo =
            when (appType) {
                AppType.EDUCATOR -> educatorService.getEducator(member).name.isEmpty()
                AppType.LEARNER -> learnerService.getLearner(member).name.isEmpty()
            }

        return AuthResponseConverter.toLoginDTO(member, token, needMemberInfo)
    }

    fun login(
        appType: AppType,
        request: AuthRequest.CommonLoginDTO,
    ): AuthResponse.LoginDTO {
        val member = memberService.getMemberByEmail(appType, Provider.COMMON, request)
        val token = jwtService.createServiceToken(member)

        val needMemberInfo =
            when (appType) {
                AppType.EDUCATOR -> educatorService.getEducator(member).name.isEmpty()
                AppType.LEARNER -> learnerService.getLearner(member).name.isEmpty()
            }

        return AuthResponseConverter.toLoginDTO(member, token, needMemberInfo)
    }
}
