package io.say.better.domain.member.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.domain.member.application.converter.ConnectConverter;
import io.say.better.storage.mysql.dao.repository.ConnectReadRepository;
import io.say.better.storage.mysql.dao.repository.ConnectWriteRepository;
import io.say.better.storage.mysql.domain.entity.Connect;
import io.say.better.storage.mysql.domain.entity.Member;
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
