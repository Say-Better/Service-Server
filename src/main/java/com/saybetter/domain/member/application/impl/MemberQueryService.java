package com.saybetter.domain.member.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.member.dao.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {

	private final MemberRepository memberRepository;
}
