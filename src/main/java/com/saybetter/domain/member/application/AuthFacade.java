package com.saybetter.domain.member.application;

import org.springframework.stereotype.Component;

import com.saybetter.domain.member.application.impl.MemberService;
import com.saybetter.domain.member.domain.Member;
import com.saybetter.global.common.constant.RoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {

	private final MemberService memberService;

	public void assignUserRole(RoleType role) {
		Member member = memberService.getCurrentMember();
		memberService.assignUserRole(member.getEmail(), role);
	}
}
