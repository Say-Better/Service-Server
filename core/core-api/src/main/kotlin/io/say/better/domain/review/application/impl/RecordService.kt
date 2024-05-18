package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.dao.repository.RecordReadRepository
import io.say.better.storage.mysql.dao.repository.RecordWriteRepository
import io.say.better.storage.mysql.domain.entity.Record
import org.springframework.stereotype.Service

@Service
class RecordService(
        private val recordReadRepository: RecordReadRepository,
        private val recordWriteRepository: RecordWriteRepository,
) {
    fun createRecord(record: Record): Record {
        return recordWriteRepository.save(record)
    }
}
