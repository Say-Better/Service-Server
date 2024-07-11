package io.say.better.domain.solution.application.impl

import io.say.better.core.common.code.status.ErrorStatus.END_PROGRESS_NOT_FOUND
import io.say.better.core.common.exception.GeneralException
import io.say.better.storage.mysql.domains.progress.entity.Progress
import io.say.better.storage.mysql.domains.progress.repository.ProgressReadRepository
import io.say.better.storage.mysql.domains.progress.repository.ProgressWriteRepository
import org.springframework.stereotype.Service

@Service
class ProgressService(
    private val progressWriteRepository: ProgressWriteRepository,
    private val progressReadRepository: ProgressReadRepository,
) {
    fun createProgress(progress: Progress): Progress = progressWriteRepository.save(progress)

    fun getProgress(progressId: Long): Progress =
        progressReadRepository.findById(progressId).orElseThrow {
            throw GeneralException(END_PROGRESS_NOT_FOUND)
        }
}
