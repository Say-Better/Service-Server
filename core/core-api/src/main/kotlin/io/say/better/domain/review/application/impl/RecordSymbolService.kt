package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.domains.review.entity.RecordSymbol
import io.say.better.storage.mysql.domains.review.repository.RecordSymbolReadRepository
import io.say.better.storage.mysql.domains.review.repository.RecordSymbolWriteRepository
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
