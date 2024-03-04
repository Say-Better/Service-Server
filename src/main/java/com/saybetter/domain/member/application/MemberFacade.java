package com.saybetter.domain.member.application;

import org.springframework.stereotype.Component;

import com.saybetter.domain.member.application.impl.ConnectService;
import com.saybetter.domain.member.application.impl.MemberService;
import com.saybetter.domain.member.domain.Member;
import com.saybetter.domain.member.exception.MemberException;
import com.saybetter.global.common.code.status.ErrorStatus;
import com.saybetter.global.utils.CodeUtil;
import com.saybetter.global.utils.RedisUtil;

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
