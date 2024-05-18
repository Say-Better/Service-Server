package io.say.better.domain.solution.application.impl

import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.common.exception.GeneralException
import io.say.better.storage.mysql.dao.repository.SolutionReadRepository
import io.say.better.storage.mysql.dao.repository.SolutionWriteRepository
import io.say.better.storage.mysql.domain.entity.Solution
import org.springframework.stereotype.Service

@Service
class SolutionService(
        private val solutionReadRepository: SolutionReadRepository,
        private val solutionWriteRepository: SolutionWriteRepository
) {

    fun createSolution(newSolution: Solution): Solution {
        return solutionWriteRepository.save(newSolution)
    }

    fun getSolution(solutionId: Long): Solution {
        return solutionReadRepository.findById(solutionId).orElseThrow() {
            throw GeneralException(ErrorStatus.START_SOLUTION_NOT_FOUND)
        };
    }
}
