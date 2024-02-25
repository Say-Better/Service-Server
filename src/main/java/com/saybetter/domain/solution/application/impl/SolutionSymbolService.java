package com.saybetter.domain.solution.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saybetter.domain.solution.dao.repository.SolutionSymbolReadRepository;
import com.saybetter.domain.solution.dao.repository.SolutionSymbolWriteRepository;
import com.saybetter.domain.solution.domain.SolutionSymbol;

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
