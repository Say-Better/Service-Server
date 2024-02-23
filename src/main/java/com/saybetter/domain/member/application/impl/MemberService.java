package com.saybetter.domain.member.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.member.dao.repository.MemberReadRepository;
import com.saybetter.domain.member.dao.repository.MemberWriteRepository;
import com.saybetter.domain.member.domain.Member;
import com.saybetter.domain.member.exception.MemberException;
import com.saybetter.global.common.code.status.ErrorStatus;
import com.saybetter.global.common.constant.RoleType;
import com.saybetter.global.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final SecurityUtil securityUtil;

	private final MemberReadRepository memberReadRepository;
	private final MemberWriteRepository memberWriteRepository;

	public Member getCurrentMember() {
		String email = securityUtil.getCurrentUserEmail();
		return getMember(email);
	}

	public Member getMember(String email) {
		return memberReadRepository.findByEmail(email)
				.orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
	}

	public void assignUserRole(String userEmail, RoleType role) {
		Member member = getMember(userEmail);

		if (member.getRole() != RoleType.NONE)
			throw new MemberException(ErrorStatus.MEMBER_HAVE_ROLE_SIGN);

		member.assignRole(role);
		memberWriteRepository.save(member);
	}
}
