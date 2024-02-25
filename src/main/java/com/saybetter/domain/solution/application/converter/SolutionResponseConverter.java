package com.saybetter.domain.solution.application.converter;

import java.util.List;

import com.saybetter.domain.solution.ui.dto.SolutionResponse;
import com.saybetter.domain.symbol.domain.Symbol;

public class SolutionResponseConverter {

	private SolutionResponseConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static SolutionResponse.SymbolRecommend toSymbolRecommend(String name, List<Symbol> symbols) {
		List<SolutionResponse.SymbolInfo> symbolInfos = symbols.stream()
				.map(SolutionResponseConverter::toSymbolInfo)
				.toList();

		return SolutionResponse.SymbolRecommend.builder()
				.name(name)
				.symbols(symbolInfos)
				.build();
	}

	private static SolutionResponse.SymbolInfo toSymbolInfo(Symbol symbol) {
		return SolutionResponse.SymbolInfo.builder()
				.description(symbol.getTitle())
				.imageUrl(symbol.getImgUrl())
				.build();
	}

}
