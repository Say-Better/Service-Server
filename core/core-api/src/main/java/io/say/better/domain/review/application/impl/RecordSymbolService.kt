package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.dao.repository.RecordSymbolReadRepository
import io.say.better.storage.mysql.dao.repository.RecordSymbolWriteRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class RecordSymbolService(
        private val recordSymbolReadRepository: RecordSymbolReadRepository,
        private val recordSymbolWriteRepository: RecordSymbolWriteRepository,
)
