package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Record
import org.springframework.data.jpa.repository.JpaRepository

interface RecordWriteRepository : JpaRepository<Record, Long>
