package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.domains.review.entity.Review
import io.say.better.storage.mysql.domains.review.repository.ReviewReadRepository
import io.say.better.storage.mysql.domains.review.repository.ReviewWriteRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewReadRepository: ReviewReadRepository,
    private val reviewWriteRepository: ReviewWriteRepository,
) {
    fun createReview(review: Review): Review = reviewWriteRepository.save(review)

    fun getReview(reviewId: Long): Review = reviewReadRepository.findById(reviewId).get()
}
