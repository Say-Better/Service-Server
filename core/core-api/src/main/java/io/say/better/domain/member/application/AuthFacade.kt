package io.say.better.domain.member.application;

import org.springframework.stereotype.Component;

import io.say.better.domain.member.application.impl.MemberService;
import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.core.enums.RoleType;
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
