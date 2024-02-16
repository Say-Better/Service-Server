package com.saybetter.domain.solution.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.solution.dao.repository.SolutionSymbolReadRepository;
import com.saybetter.domain.solution.dao.repository.SolutionSymbolWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolutionSymbolService {

	private final SolutionSymbolReadRepository solutionSymbolReadRepository;
	private final SolutionSymbolWriteRepository solutionSymbolWriteRepository;

}
