package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.domain.solution.ui.dto.SolutionRequest.EndSolution
import io.say.better.storage.mysql.domain.entity.Record
import io.say.better.storage.mysql.domain.entity.Review

class RecordConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toRecord(endSolution: EndSolution, review: Review): Record {
            return Record.builder()
                    .orderNum(endSolution.orderNum)
                    .review(review)
                    .build()
        }
    }
}
