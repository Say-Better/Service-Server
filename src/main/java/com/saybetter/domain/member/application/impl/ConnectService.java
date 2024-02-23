package com.saybetter.domain.member.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.member.application.converter.ConnectConverter;
import com.saybetter.domain.member.dao.repository.ConnectReadRepository;
import com.saybetter.domain.member.dao.repository.ConnectWriteRepository;
import com.saybetter.domain.member.domain.Connect;
import com.saybetter.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectService {

	private final ConnectReadRepository connectReadRepository;
	private final ConnectWriteRepository connectWriteRepository;

	public void connect(Member educator, Member learner) {
		Connect newConnect = ConnectConverter.toConnect(educator, learner);
		connectWriteRepository.save(newConnect);
	}
}
