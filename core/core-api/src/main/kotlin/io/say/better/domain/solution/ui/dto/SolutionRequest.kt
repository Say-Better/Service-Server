package io.say.better.domain.solution.ui.dto

class SolutionRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class CreateSolution(
            val title: String? = null,
            val baselineSessionTime: Int? = null,
            val interCompleteThresh: Double? = null,
            val interMaintainTerm: Int? = null,
            val commOptTimes: Int? = null,
            val commOptCnt: Int? = null,
            val symbols: List<String>? = null,
    )

    data class StartSolution(
            val sessionOrder: Int? = null,
            val solutionId: Long? = null,
            val nowStep: String? = null,
            val sessionGoal: String? = null,
            val sessionDesc: String? = null
    )

    data class EndSolution(
            val solutionId: Long? = null,
            val progressId: Long? = null,
            val orderNum: Int? = null,
            val createRecordSymbols: List<CreateRecordSymbol>? = null,
            var reviewId: Long? = null
    )

    data class CreateRecordSymbol(
            val symbolId: Long? = null,
            val touchOrder: Int? = null,
            val touchTime: String? = null
    )
}
