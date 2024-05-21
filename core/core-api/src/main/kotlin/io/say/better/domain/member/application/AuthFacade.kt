package io.say.better.domain.member.application

import io.say.better.core.enums.RoleType
import io.say.better.domain.member.application.impl.MemberService
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val memberService: MemberService,
) {
    fun assignUserRole(role: RoleType) {
        val member = memberService.currentMember()
        memberService.assignUserRole(member.email!!, role)
    }
}
