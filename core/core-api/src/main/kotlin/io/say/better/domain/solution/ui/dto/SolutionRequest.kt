package io.say.better.domain.solution.ui.dto

import io.say.better.storage.mysql.domain.constant.ProgressState

class SolutionRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class CreateSolution constructor(
        val learnerEmail: String? = null,
        val nowState: ProgressState? = null,
        val educationGoal: String? = null,
        val description: String? = null,
        val title: String? = null,
        val commOptTimes: Int? = null,
        val commOptCnt: Int? = null
    )
}
