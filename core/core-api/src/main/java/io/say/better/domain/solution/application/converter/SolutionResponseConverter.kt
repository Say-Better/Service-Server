package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionResponse
import io.say.better.domain.solution.ui.dto.SolutionResponse.SymbolList
import io.say.better.storage.mysql.domain.entity.Symbol

class SolutionResponseConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSymbolRecommend(name: String, symbols: List<Symbol>): SymbolList {
            val symbolInfos = symbols.stream()
                    .map { symbol: Symbol -> toSymbolInfo(symbol) }
                    .toList()

            return SymbolList(name, symbolInfos)
        }

        private fun toSymbolInfo(symbol: Symbol): SolutionResponse.SymbolInfo {
            return SolutionResponse.SymbolInfo(symbol.title, symbol.imgUrl)
        }
    }
}
