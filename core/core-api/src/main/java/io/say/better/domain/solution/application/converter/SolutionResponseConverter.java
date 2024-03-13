package io.say.better.domain.solution.application.converter;

import java.util.List;

import io.say.better.domain.solution.ui.dto.SolutionResponse;
import io.say.better.storage.mysql.domain.entity.Symbol;

public class SolutionResponseConverter {

	private SolutionResponseConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static SolutionResponse.SymbolList toSymbolRecommend(String name, List<Symbol> symbols) {
		List<SolutionResponse.SymbolInfo> symbolInfos = symbols.stream()
				.map(SolutionResponseConverter::toSymbolInfo)
				.toList();

		return SolutionResponse.SymbolList.builder()
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
