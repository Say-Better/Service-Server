package io.say.better.domain.solution.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.SolutionReadRepository;
import io.say.better.storage.mysql.dao.repository.SolutionWriteRepository;
import io.say.better.storage.mysql.domain.entity.Solution;
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
