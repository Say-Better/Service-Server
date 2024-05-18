package io.say.better.domain.solution.application.impl

import io.say.better.storage.mysql.dao.repository.SolutionSymbolReadRepository
import io.say.better.storage.mysql.dao.repository.SolutionSymbolWriteRepository
import io.say.better.storage.mysql.domain.entity.SolutionSymbol
import org.springframework.stereotype.Service

@Service
class SolutionSymbolService(
    private val solutionSymbolReadRepository: SolutionSymbolReadRepository,
    private val solutionSymbolWriteRepository: SolutionSymbolWriteRepository
) {
    fun createSolutionSymbols(solutionSymbols: List<SolutionSymbol?>) {
        solutionSymbolWriteRepository.saveAll(solutionSymbols)
    }
}
