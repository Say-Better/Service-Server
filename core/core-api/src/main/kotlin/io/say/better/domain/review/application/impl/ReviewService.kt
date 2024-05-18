package io.say.better.domain.review.application.impl

import io.say.better.global.common.exception.GeneralException
import io.say.better.storage.mysql.dao.repository.ReviewReadRepository
import io.say.better.storage.mysql.dao.repository.ReviewWriteRepository
import io.say.better.storage.mysql.domain.entity.Review
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
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
