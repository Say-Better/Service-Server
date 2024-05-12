package io.say.better.domain.solution.application.converter

import io.say.better.storage.mysql.domain.entity.Progress
import io.say.better.storage.mysql.domain.entity.Review

class ReviewConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toReview(progress: Progress): Review {
            return Review
                    .builder()
                    .progress(progress)
                    .build()
        }
    }
}
