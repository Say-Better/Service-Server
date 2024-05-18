package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewWriteRepository : JpaRepository<Review, Long>
