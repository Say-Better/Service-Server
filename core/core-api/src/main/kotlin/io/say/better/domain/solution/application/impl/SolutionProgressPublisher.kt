package io.say.better.domain.solution.application.impl

import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.storage.rabbitmq.util.MessageQueueUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class SolutionProgressPublisher(
        private val messageQueueUtil: MessageQueueUtil
) {
    fun publishRecord(endSolution: SolutionRequest.EndSolution) {
        messageQueueUtil.publish(endSolution)
    }
}
