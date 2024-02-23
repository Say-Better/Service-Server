package com.saybetter.domain.member.application;

import org.springframework.stereotype.Component;

import com.saybetter.domain.member.application.impl.MemberService;
import com.saybetter.global.common.constant.RoleType;
import com.saybetter.global.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {

	private final SecurityUtil securityUtil;
	private final MemberService memberService;

	public void assignUserRole(RoleType role) {
		String userEmail = securityUtil.getCurrentUserEmail();
		memberService.assignUserRole(userEmail, role);
	}
}
