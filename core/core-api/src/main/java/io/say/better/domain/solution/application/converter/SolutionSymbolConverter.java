package io.say.better.domain.solution.application.converter;

import java.util.List;

import io.say.better.storage.mysql.domain.entity.Solution;
import io.say.better.storage.mysql.domain.entity.SolutionSymbol;
import io.say.better.storage.mysql.domain.entity.Symbol;

public class SolutionSymbolConverter {

	private SolutionSymbolConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static List<SolutionSymbol> toSolutionSymbols(Solution savedSolution, List<Symbol> symbols) {
		return symbols.stream()
				.map(symbol -> toSolutionSymbol(savedSolution, symbol))
				.toList();
	}

	private static SolutionSymbol toSolutionSymbol(Solution solution, Symbol symbol) {
		return SolutionSymbol.builder()
				.solution(solution)
				.symbol(symbol)
				.build();
	}

}
