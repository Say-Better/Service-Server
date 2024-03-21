package io.say.better.domain.solution.application.impl

import io.say.better.storage.mysql.dao.repository.SolutionReadRepository
import io.say.better.storage.mysql.dao.repository.SolutionWriteRepository
import io.say.better.storage.mysql.domain.entity.Solution
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class SolutionService(
        private val solutionReadRepository: SolutionReadRepository,
        private val solutionWriteRepository: SolutionWriteRepository
) {

    fun createSolution(newSolution: Solution): Solution {
        return solutionWriteRepository!!.save(newSolution)
    }
}
