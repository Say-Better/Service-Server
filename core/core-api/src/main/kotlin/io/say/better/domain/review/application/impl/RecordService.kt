package io.say.better.domain.review.application.impl

import io.say.better.storage.mysql.domains.review.entity.Record
import io.say.better.storage.mysql.domains.review.entity.Review
import io.say.better.storage.mysql.domains.review.repository.RecordReadRepository
import io.say.better.storage.mysql.domains.review.repository.RecordWriteRepository
import org.springframework.stereotype.Service

@Service
class RecordService(
    private val recordReadRepository: RecordReadRepository,
    private val recordWriteRepository: RecordWriteRepository,
) {
    fun createRecord(record: Record): Record = recordWriteRepository.save(record)

    fun getRecordByReview(review: Review): Record? = recordReadRepository.getRecordByReview(review)

    fun updateVoice(
        voiceUrl: String,
        record: Record,
    ) {
        record.saveVoice(voiceUrl)
        recordWriteRepository.save(record)
    }
}
