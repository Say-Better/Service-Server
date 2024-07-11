package io.say.better.storage.mysql.domains.review.repository

import io.say.better.storage.mysql.domains.review.entity.Record
import org.springframework.data.jpa.repository.JpaRepository

interface RecordReadRepository : JpaRepository<Record, Long>
