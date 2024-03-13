package io.say.better.domain.solution.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.say.better.client.symbol.api.RecommendClient;
import io.say.better.client.symbol.api.dto.RecommendResult;
import io.say.better.domain.member.application.impl.MemberService;
import io.say.better.domain.solution.application.converter.SolutionConverter;
import io.say.better.domain.solution.application.converter.SolutionResponseConverter;
import io.say.better.domain.solution.application.converter.SolutionSymbolConverter;
import io.say.better.domain.solution.application.impl.SolutionService;
import io.say.better.domain.solution.application.impl.SolutionSymbolService;
import io.say.better.domain.solution.ui.dto.SolutionRequest;
import io.say.better.domain.solution.ui.dto.SolutionResponse;
import io.say.better.domain.symbol.application.impl.SymbolService;
import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.storage.mysql.domain.entity.Solution;
import io.say.better.storage.mysql.domain.entity.SolutionSymbol;
import io.say.better.storage.mysql.domain.entity.Symbol;
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
