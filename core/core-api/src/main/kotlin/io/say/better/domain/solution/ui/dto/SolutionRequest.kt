package io.say.better.domain.solution.ui.dto

import io.say.better.storage.mysql.domain.constant.ProgressState

class SolutionRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class CreateSolution constructor(
        val learnerEmail: String,
        val nowState: ProgressState,
        val educationGoal: String,
        val description: String,
        val title: String,
        val commOptTimes: Int,
        val commOptCnt: Int,
    )

    data class StartSolution(
        val sessionOrder: Int,
        val solutionId: Long,
        val nowStep: String,
        val sessionGoal: String? = null,
        val sessionDesc: String? = null,
    )

    data class EndSolution(
        val solutionId: Long,
        val progressId: Long,
        val orderNum: Int,
        val createRecordSymbols: List<CreateRecordSymbol>,
        var reviewId: Long? = null,
    )

    data class CreateRecordSymbol(
        val symbolId: Long,
        val touchOrder: Int,
        val touchTime: String,
    )
}
