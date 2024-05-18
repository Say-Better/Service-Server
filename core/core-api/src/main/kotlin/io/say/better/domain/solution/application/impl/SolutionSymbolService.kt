package io.say.better.domain.solution.application.impl

import io.say.better.storage.mysql.dao.repository.SolutionSymbolReadRepository
import io.say.better.storage.mysql.dao.repository.SolutionSymbolWriteRepository
import io.say.better.storage.mysql.domain.entity.SolutionSymbol
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class SolutionSymbolService(
    private val solutionSymbolReadRepository: SolutionSymbolReadRepository,
    private val solutionSymbolWriteRepository: SolutionSymbolWriteRepository
) {
    fun createSolutionSymbols(solutionSymbols: List<SolutionSymbol?>) {
        solutionSymbolWriteRepository.saveAll(solutionSymbols)
    }
}
