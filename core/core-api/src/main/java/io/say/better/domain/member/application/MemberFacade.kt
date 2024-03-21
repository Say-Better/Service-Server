package io.say.better.domain.member.application;

import org.springframework.stereotype.Component;

import io.say.better.domain.member.application.impl.ConnectService;
import io.say.better.domain.member.application.impl.MemberService;
import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.domain.member.exception.MemberException;
import io.say.better.global.common.code.status.ErrorStatus;
import io.say.better.global.utils.CodeUtil;
import io.say.better.storage.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFacade {

	private final CodeUtil codeUtil;
	private final RedisUtil redisUtil;
	private final MemberService memberService;
	private final ConnectService connectService;

	public String createConnectCode() {
		Member member = memberService.getCurrentMember();
		String code = codeUtil.createConnectCode();
		redisUtil.setConnectCode(code, member.getEmail());

		return code;
	}

	public void connect(String code) {
		String email = redisUtil.getData(code);

		if (email == null) {
			throw new MemberException(ErrorStatus.CONNECT_CODE_NOT_VALID);
		}

		redisUtil.deleteData(code);

		Member educator = memberService.getCurrentMember();
		Member learner = memberService.getMember(email);
		connectService.connect(educator, learner);
	}
}
