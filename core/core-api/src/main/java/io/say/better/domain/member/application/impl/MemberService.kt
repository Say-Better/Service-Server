package io.say.better.domain.member.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.MemberReadRepository;
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository;
import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.domain.member.exception.MemberException;
import io.say.better.global.common.code.status.ErrorStatus;
import io.say.better.core.enums.RoleType;
import io.say.better.global.utils.SecurityUtil;
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

		if (member.getRole() != RoleType.NONE) {
			throw new MemberException(ErrorStatus.MEMBER_HAVE_ROLE_SIGN);
		}

		member.assignRole(role);
		memberWriteRepository.save(member);
	}
}
