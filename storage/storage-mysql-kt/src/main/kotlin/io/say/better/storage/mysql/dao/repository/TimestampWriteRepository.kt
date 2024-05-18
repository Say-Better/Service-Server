package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Timestamp
import org.springframework.data.jpa.repository.JpaRepository

interface TimestampWriteRepository : JpaRepository<Timestamp, Long>
