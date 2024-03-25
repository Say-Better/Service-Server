package io.say.better.domain.solution.ui.dto

class SolutionRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class CreateSolution (
        val title: String? = null,
        val baselineSessionTime: Int? = null,
        val interCompleteThresh: Double? = null,
        val interMaintainTerm: Int? = null,
        val commOptTimes: Int? = null,
        val commOptCnt: Int? = null,
        val symbols: List<String>? = null,
    )
}
