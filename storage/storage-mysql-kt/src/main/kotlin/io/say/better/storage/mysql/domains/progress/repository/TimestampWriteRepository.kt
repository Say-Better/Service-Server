package io.say.better.storage.mysql.domains.progress.repository

import io.say.better.storage.mysql.domains.progress.entity.Timestamp
import org.springframework.data.jpa.repository.JpaRepository

interface TimestampWriteRepository : JpaRepository<Timestamp, Long>
