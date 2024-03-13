package io.say.better.domain.solution.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.AssignReadRepository;
import io.say.better.storage.mysql.dao.repository.AssignWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignService {

	private final AssignReadRepository assignReadRepository;
	private final AssignWriteRepository assignWriteRepository;

}
