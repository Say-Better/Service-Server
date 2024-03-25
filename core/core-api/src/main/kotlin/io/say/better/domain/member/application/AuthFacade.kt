package io.say.better.domain.member.application

import io.say.better.core.enums.RoleType
import io.say.better.domain.member.application.impl.MemberService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class AuthFacade(
        private val memberService: MemberService,
) {

    fun assignUserRole(role: RoleType?) {
        val member = memberService!!.currentMember()
        memberService.assignUserRole(member.email, role)
    }
}
