package io.say.better.domain.solution.application.impl

import io.say.better.storage.mysql.domains.solution.entity.SolutionSymbol
import io.say.better.storage.mysql.domains.solution.repository.SolutionSymbolReadRepository
import io.say.better.storage.mysql.domains.solution.repository.SolutionSymbolWriteRepository
import org.springframework.stereotype.Service

@Service
class SolutionSymbolService(
    private val solutionSymbolReadRepository: SolutionSymbolReadRepository,
    private val solutionSymbolWriteRepository: SolutionSymbolWriteRepository,
) {
    fun createSolutionSymbols(solutionSymbols: List<SolutionSymbol?>) {
        solutionSymbolWriteRepository.saveAll(solutionSymbols)
    }
}
