package com.saybetter.domain.solution.application.converter;

import java.util.List;

import com.saybetter.domain.solution.domain.Solution;
import com.saybetter.domain.solution.domain.SolutionSymbol;
import com.saybetter.domain.symbol.domain.Symbol;

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
