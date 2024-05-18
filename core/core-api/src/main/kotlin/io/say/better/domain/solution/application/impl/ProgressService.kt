package io.say.better.domain.solution.application.impl

import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.common.exception.GeneralException
import io.say.better.storage.mysql.dao.repository.ProgressReadRepository
import io.say.better.storage.mysql.dao.repository.ProgressWriteRepository
import io.say.better.storage.mysql.domain.entity.Progress
import org.springframework.stereotype.Service

@Service
class ProgressService(
        private val progressWriteRepository: ProgressWriteRepository,
        private val progressReadRepository: ProgressReadRepository
) {

    fun createProgress(progress: Progress): Progress {
        return progressWriteRepository.save(progress)
    }

    fun getProgress(progressId: Long): Progress {
        return progressReadRepository.findById(progressId).orElseThrow() {
            throw GeneralException(ErrorStatus.END_PROGRESS_NOT_FOUND)
        }
    }
}
