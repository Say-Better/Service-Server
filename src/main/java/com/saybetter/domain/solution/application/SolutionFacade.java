package com.saybetter.domain.solution.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.saybetter.client.api.RecommendClient;
import com.saybetter.client.api.dto.RecommendResult;
import com.saybetter.domain.solution.application.converter.SolutionResponseConverter;
import com.saybetter.domain.solution.ui.dto.SolutionResponse;
import com.saybetter.domain.symbol.application.impl.SymbolService;
import com.saybetter.domain.symbol.domain.Symbol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolutionFacade {

	private final SymbolService symbolService;
	private final RecommendClient recommendClient;

	public SolutionResponse.SymbolRecommend recommendSymbol(String name) {
		RecommendResult.SymbolRecommend recommend = recommendClient.recommend(name);
		List<Symbol> symbols = symbolService.getSymbols(recommend.getSymbols());
		return SolutionResponseConverter.toSymbolRecommend(name, symbols);
	}

}
