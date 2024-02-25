package com.saybetter.domain.solution.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.saybetter.client.api.RecommendClient;
import com.saybetter.client.api.dto.RecommendResult;
import com.saybetter.domain.member.application.impl.MemberService;
import com.saybetter.domain.member.domain.Member;
import com.saybetter.domain.solution.application.converter.SolutionConverter;
import com.saybetter.domain.solution.application.converter.SolutionResponseConverter;
import com.saybetter.domain.solution.application.converter.SolutionSymbolConverter;
import com.saybetter.domain.solution.application.impl.SolutionService;
import com.saybetter.domain.solution.application.impl.SolutionSymbolService;
import com.saybetter.domain.solution.domain.Solution;
import com.saybetter.domain.solution.domain.SolutionSymbol;
import com.saybetter.domain.solution.ui.dto.SolutionRequest;
import com.saybetter.domain.solution.ui.dto.SolutionResponse;
import com.saybetter.domain.symbol.application.impl.SymbolService;
import com.saybetter.domain.symbol.domain.Symbol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolutionFacade {

	private final SolutionService solutionService;
	private final SolutionSymbolService solutionSymbolService;

	private final MemberService memberService;
	private final SymbolService symbolService;

	private final RecommendClient recommendClient;

	@Transactional(readOnly = true)
	public SolutionResponse.SymbolList recommendSymbol(String name) {
		RecommendResult.SymbolRecommend recommend = recommendClient.recommend(name);
		List<Symbol> symbols = symbolService.getSymbols(recommend.getSymbols());
		return SolutionResponseConverter.toSymbolRecommend(name, symbols);
	}

	@Transactional(readOnly = true)
	public SolutionResponse.SymbolList searchSymbol(String name) {
		List<Symbol> symbols = symbolService.getSymbols(name);
		return SolutionResponseConverter.toSymbolRecommend(name, symbols);
	}

	@Transactional
	public void createSolution(SolutionRequest.CreateSolution request) {
		Member member = memberService.getCurrentMember();
		Solution newSolution = SolutionConverter.toSolution(request, member);
		Solution savedSolution = solutionService.createSolution(newSolution);

		List<Symbol> symbols = symbolService.getSymbols(request.getSymbols());
		List<SolutionSymbol> solutionSymbols = SolutionSymbolConverter.toSolutionSymbols(savedSolution, symbols);
		solutionSymbolService.createSolutionSymbols(solutionSymbols);

		savedSolution.onActivated();
	}

}
