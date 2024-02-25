package com.saybetter.domain.solution.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.solution.dao.repository.SolutionReadRepository;
import com.saybetter.domain.solution.dao.repository.SolutionWriteRepository;
import com.saybetter.domain.solution.domain.Solution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolutionService {

	private final SolutionReadRepository solutionReadRepository;
	private final SolutionWriteRepository solutionWriteRepository;

	public Solution createSolution(Solution newSolution) {
		return solutionWriteRepository.save(newSolution);
	}
}
