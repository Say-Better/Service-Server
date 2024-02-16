package com.saybetter.domain.solution.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.solution.dao.repository.AssignReadRepository;
import com.saybetter.domain.solution.dao.repository.AssignWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignService {

	private final AssignReadRepository assignReadRepository;
	private final AssignWriteRepository assignWriteRepository;

}
