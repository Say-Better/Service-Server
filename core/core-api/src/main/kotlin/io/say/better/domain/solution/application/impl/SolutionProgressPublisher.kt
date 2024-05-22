package io.say.better.domain.solution.application.impl

import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.storage.rabbitmq.util.MessageQueueUtil
import org.springframework.stereotype.Service

@Service
class SolutionProgressPublisher(
    private val messageQueueUtil: MessageQueueUtil,
) {
    fun publishRecord(endSolution: SolutionRequest.EndSolution) {
        messageQueueUtil.publish(endSolution)
    }
}
