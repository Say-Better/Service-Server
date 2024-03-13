package io.say.better.domain.solution.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.SolutionSymbolReadRepository;
import io.say.better.storage.mysql.dao.repository.SolutionSymbolWriteRepository;
import io.say.better.storage.mysql.domain.entity.SolutionSymbol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolutionSymbolService {

	private final SolutionSymbolReadRepository solutionSymbolReadRepository;
	private final SolutionSymbolWriteRepository solutionSymbolWriteRepository;

	public void createSolutionSymbols(List<SolutionSymbol> solutionSymbols) {
		solutionSymbolWriteRepository.saveAll(solutionSymbols);
	}

}
