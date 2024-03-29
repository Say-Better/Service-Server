package io.say.better.domain.member.application

import io.say.better.domain.member.application.impl.ConnectService
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.member.exception.MemberException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.utils.CodeUtil
import io.say.better.storage.redis.RedisUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class MemberFacade(
        private val connectService: ConnectService,
        private val memberService: MemberService,
        private val codeUtil: CodeUtil,
        private val redisUtil: RedisUtil
) {

    fun createConnectCode(): String {
        val member = memberService!!.currentMember()
        val code = codeUtil!!.createConnectCode()
        redisUtil!!.setConnectCode(code, member.email)

        return code
    }

    fun connect(code: String?) {
        val email = redisUtil!!.getData(code)
                ?: throw MemberException(ErrorStatus.CONNECT_CODE_NOT_VALID)

        redisUtil.deleteData(code)

        val educator = memberService!!.currentMember()
        val learner = memberService.getMember(email)
        connectService!!.connect(educator, learner)
    }
}
