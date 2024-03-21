package io.say.better.domain.member.application.impl

import io.say.better.core.enums.RoleType
import io.say.better.domain.member.exception.MemberException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.utils.SecurityUtil
import io.say.better.storage.mysql.dao.repository.MemberReadRepository
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository
import io.say.better.storage.mysql.domain.entity.Member
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class MemberService(
    private val securityUtil: SecurityUtil,
    private val memberReadRepository: MemberReadRepository,
    private val memberWriteRepository: MemberWriteRepository
) {

    fun currentMember(): Member {
        val email = securityUtil!!.currentUserEmail
        return getMember(email)
    }

    fun getMember(email: String?): Member {
        return memberReadRepository!!.findByEmail(email)
            .orElseThrow { MemberException(ErrorStatus.MEMBER_NOT_FOUND) }
    }

    fun assignUserRole(userEmail: String?, role: RoleType?) {
        val member = getMember(userEmail)

        if (member.role != RoleType.NONE) {
            throw MemberException(ErrorStatus.MEMBER_HAVE_ROLE_SIGN)
        }

        member.assignRole(role)
        memberWriteRepository!!.save(member)
    }
}
