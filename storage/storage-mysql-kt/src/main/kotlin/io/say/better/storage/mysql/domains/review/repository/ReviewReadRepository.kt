package io.say.better.storage.mysql.domains.review.repository

import io.say.better.storage.mysql.domains.progress.entity.Progress
import io.say.better.storage.mysql.domains.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewReadRepository : JpaRepository<Review, Long> {
    fun findByProgress(progress: Progress): Review?
}
