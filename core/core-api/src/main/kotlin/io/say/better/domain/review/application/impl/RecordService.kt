package io.say.better.domain.review.application.impl

import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.core.common.exception.GeneralException
import io.say.better.domain.review.ui.dto.ReviewRequest
import io.say.better.storage.mysql.domains.review.entity.Record
import io.say.better.storage.mysql.domains.review.entity.Review
import io.say.better.storage.mysql.domains.review.repository.RecordReadRepository
import io.say.better.storage.mysql.domains.review.repository.RecordWriteRepository
import io.say.better.storage.mysql.domains.review.type.ReactionType
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

    fun updateReactionTypeOnRecord(request: List<ReviewRequest.SubmitReview>) {
        for (submit in request) {
            val record: Record =
                recordReadRepository
                    .findById(submit.recordId)
                    .orElseThrow { GeneralException(ErrorStatus.SUBMIT_REVIEW_TARGET_RECORD_NOT_FOUND) }

            record.updateReactionType(ReactionType.valueOf(submit.reactionType))
            recordWriteRepository.save(record)
        }
    }
}
