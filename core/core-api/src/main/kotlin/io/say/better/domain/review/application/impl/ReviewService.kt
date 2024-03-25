package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.dao.repository.ReviewReadRepository
import io.say.better.storage.mysql.dao.repository.ReviewWriteRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class ReviewService(
        private val reviewReadRepository: ReviewReadRepository,
        private val reviewWriteRepository: ReviewWriteRepository,
)
