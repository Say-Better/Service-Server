package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateSolution
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Solution

class SolutionConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSolution(request: CreateSolution, educator: Educator?): Solution {
            return Solution.builder()
                .writer(educator)
                .title(request.title)
                .baselineSessionTime(request.baselineSessionTime)
                .interCompleteThresh(request.interCompleteThresh)
                .interMaintainTerm(request.interMaintainTerm)
                .commOptTimes(request.commOptTimes)
                .commOptCnt(request.commOptCnt)
                .build()
        }
    }
}
