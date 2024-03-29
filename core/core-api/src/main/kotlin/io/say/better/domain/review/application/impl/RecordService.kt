package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.dao.repository.RecordReadRepository
import io.say.better.storage.mysql.dao.repository.RecordWriteRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class RecordService(
        private val recordReadRepository: RecordReadRepository,
        private val recordWriteRepository: RecordWriteRepository
)
