package io.say.better.domain.solution.application.converter

import io.say.better.storage.mysql.domains.progress.entity.Progress
import io.say.better.storage.mysql.domains.review.entity.Review

class ReviewConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toReview(progress: Progress): Review {
            return Review(
                progress = progress,
            )
        }
    }
}
