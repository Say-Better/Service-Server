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
            val sessionOrder: Int,
            val solutionId: Long,
            val nowStep: String,
            val sessionGoal: String? = null,
            val sessionDesc: String? = null
    )

    data class EndSolution(
            val solutionId: Long,
            val progressId: Long,
            val orderNum: Int,
            val createRecordSymbols: List<CreateRecordSymbol>,
            var reviewId: Long? = null
    )

    data class CreateRecordSymbol(
            val symbolId: Long,
            val touchOrder: Int,
            val touchTime: String
    )
}
