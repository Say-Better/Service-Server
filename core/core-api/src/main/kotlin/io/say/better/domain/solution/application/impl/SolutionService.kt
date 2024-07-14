package io.say.better.domain.solution.application.impl

import io.say.better.core.common.code.status.ErrorStatus.START_SOLUTION_NOT_FOUND
import io.say.better.core.common.exception.GeneralException
import io.say.better.storage.mysql.domains.solution.entity.Solution
import io.say.better.storage.mysql.domains.solution.repository.SolutionReadRepository
import io.say.better.storage.mysql.domains.solution.repository.SolutionWriteRepository
import org.springframework.stereotype.Service

@Service
class SolutionService(
    private val solutionReadRepository: SolutionReadRepository,
    private val solutionWriteRepository: SolutionWriteRepository,
) {
    fun createSolution(newSolution: Solution): Solution = solutionWriteRepository.save(newSolution)

    fun getSolution(solutionId: Long): Solution =
        solutionReadRepository.findById(solutionId).orElseThrow {
            throw GeneralException(START_SOLUTION_NOT_FOUND)
        }
}
