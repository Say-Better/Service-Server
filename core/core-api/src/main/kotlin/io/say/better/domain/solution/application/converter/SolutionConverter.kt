package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateSolution
import io.say.better.storage.mysql.domains.account.entity.Educator
import io.say.better.storage.mysql.domains.account.entity.Learner
import io.say.better.storage.mysql.domains.solution.entity.Solution

class SolutionConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSolution(
            request: CreateSolution,
            educator: Educator,
            learner: Learner,
        ): Solution {
            return Solution(
                nowState = request.nowState,
                educationGoal = request.educationGoal,
                description = request.description,
                writer = educator,
                learner = learner,
                title = request.title,
                commOptTimes = request.commOptTimes,
                commOptCnt = request.commOptCnt,
            )
        }
    }
}
