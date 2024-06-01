package io.say.better.domain.member.application

import io.say.better.client.oauth.strategy.OauthSocialLogin
import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.domain.member.application.converter.AuthResponseConverter
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.domain.member.ui.dto.AuthResponse
import io.say.better.global.common.constant.AppType
import io.say.better.global.jwt.service.JwtService
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val memberService: MemberService,
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
        socialType: Provider,
        request: AuthRequest.LoginDTO,
    ): AuthResponse.LoginDTO {
        val userInfo = socialLoginStrategyMap[socialType]!!.verifyToken(request.identityToken)
        val member = memberService.getMember(socialType, userInfo)
        val token = jwtService.createServiceToken(member)

        return AuthResponseConverter.toLoginDTO(member, token)
    }

}
