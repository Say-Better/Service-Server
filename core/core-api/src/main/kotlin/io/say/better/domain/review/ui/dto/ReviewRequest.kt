package io.say.better.domain.review.ui.dto

class ReviewRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class SubmitReview constructor(
        val recordId: Long,
        val reactionType: String,
    )
}
