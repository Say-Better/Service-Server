package com.saybetter.domain.member.application;

import org.springframework.stereotype.Component;

import com.saybetter.domain.member.application.impl.MemberService;
import com.saybetter.domain.member.domain.Member;
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

	public void createConnectCode() {
		Member member = memberService.getCurrentMember();
		String code = codeUtil.createConnectCode();
		redisUtil.setConnectCode(code, member.getEmail());
	}

	public String getConnectCode() {
		Member member = memberService.getCurrentMember();
		return redisUtil.getData(member.getEmail());
	}
}
