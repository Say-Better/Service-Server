package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionRequest.StartSolution
import io.say.better.storage.mysql.domain.constant.AssignStep
import io.say.better.storage.mysql.domain.entity.Progress
import io.say.better.storage.mysql.domain.entity.Solution

class ProgressConverter {

    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toProgress(request: StartSolution, solution: Solution): Progress {
            return Progress
                    .builder()
                    .solution(solution)
                    .nowAssignStep(AssignStep.valueOf(request.nowStep))
                    .sessionOrder(request.sessionOrder)
                    .sessionGoal(request.sessionGoal)
                    .sessionDescription(request.sessionDesc)
                    .build()
        }
    }
}
