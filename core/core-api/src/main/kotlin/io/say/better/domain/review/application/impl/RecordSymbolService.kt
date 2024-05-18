package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.dao.repository.RecordSymbolReadRepository
import io.say.better.storage.mysql.dao.repository.RecordSymbolWriteRepository
import io.say.better.storage.mysql.domain.entity.RecordSymbol
import org.springframework.stereotype.Service

@Service
class RecordSymbolService(
        private val recordSymbolReadRepository: RecordSymbolReadRepository,
        private val recordSymbolWriteRepository: RecordSymbolWriteRepository,
) {

    fun createRecordSymbol(recordSymbol: RecordSymbol) {
        recordSymbolWriteRepository.save(recordSymbol)
    }
}
