package com.saybetter.domain.member.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.member.dao.repository.MemberReadRepository;
import com.saybetter.domain.member.dao.repository.MemberWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberReadRepository memberReadRepository;
	private final MemberWriteRepository memberWriteRepository;

}
