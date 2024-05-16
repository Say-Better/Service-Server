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
        val commOptCnt: Int
    )
}
