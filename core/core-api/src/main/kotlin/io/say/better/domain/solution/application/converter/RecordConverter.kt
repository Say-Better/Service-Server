package io.say.better.domain.solution.application.converter

import io.say.better.domain.solution.ui.dto.SolutionRequest.EndSolution
import io.say.better.storage.mysql.domains.review.entity.Record
import io.say.better.storage.mysql.domains.review.entity.Review

class RecordConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toRecord(
            endSolution: EndSolution,
            review: Review,
        ): Record =
            Record(
                orderNum = endSolution.orderNum,
                review = review,
            )
    }
}
