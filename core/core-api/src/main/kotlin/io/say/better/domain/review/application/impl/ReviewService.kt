package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.dao.repository.ReviewReadRepository
import io.say.better.storage.mysql.dao.repository.ReviewWriteRepository
import io.say.better.storage.mysql.domain.entity.Review
import org.springframework.stereotype.Service

@Service
class ReviewService(
        private val reviewReadRepository: ReviewReadRepository,
        private val reviewWriteRepository: ReviewWriteRepository,
) {
    fun createReview(review: Review): Review {
        return reviewWriteRepository.save(review)
    }

    fun getReview(reviewId: Long): Review {
        return reviewReadRepository.findById(reviewId).get()
    }

}
