package io.say.better.domain.solution.application.converter

import io.say.better.storage.mysql.domain.entity.Solution
import io.say.better.storage.mysql.domain.entity.SolutionSymbol
import io.say.better.storage.mysql.domain.entity.Symbol

class SolutionSymbolConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSolutionSymbols(
            savedSolution: Solution,
            symbols: List<Symbol?>,
        ): List<SolutionSymbol> {
            return symbols.stream()
                .map { symbol: Symbol? -> toSolutionSymbol(savedSolution, symbol) }
                .toList()
        }

        private fun toSolutionSymbol(
            solution: Solution,
            symbol: Symbol?,
        ): SolutionSymbol {
            return SolutionSymbol(
                solution = solution,
                symbol = symbol!!,
            )
        }
    }
}
