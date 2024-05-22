package io.say.better.domain.solution.ui.dto

class SolutionResponse private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class SymbolList(
        val name: String,
        val symbols: List<SymbolInfo>? = null,
    )

    data class SymbolInfo(
        val description: String,
        val imageUrl: String,
    )

    data class ProgressInfo(
        val progressId: Long,
    )
}
