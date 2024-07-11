package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionResponse
import io.say.better.domain.solution.ui.dto.SolutionResponse.SymbolList
import io.say.better.storage.mysql.domains.progress.entity.Progress
import io.say.better.storage.mysql.domains.symbol.entity.Symbol

class SolutionResponseConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSymbolRecommend(
            name: String,
            symbols: List<Symbol>,
        ): SymbolList {
            val symbolInfos =
                symbols.stream()
                    .map { symbol: Symbol -> toSymbolInfo(symbol) }
                    .toList()

            return SymbolList(name, symbolInfos)
        }

        private fun toSymbolInfo(symbol: Symbol): SolutionResponse.SymbolInfo {
            return SolutionResponse.SymbolInfo(symbol.title, symbol.imgUrl)
        }

        fun toProgressInfo(progress: Progress): SolutionResponse.ProgressInfo {
            return SolutionResponse.ProgressInfo(progress.progressId)
        }
    }
}
