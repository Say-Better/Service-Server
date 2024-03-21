package io.say.better.domain.solution.application

import io.say.better.client.symbol.api.RecommendClient
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.solution.application.converter.SolutionConverter
import io.say.better.domain.solution.application.converter.SolutionResponseConverter
import io.say.better.domain.solution.application.converter.SolutionSymbolConverter
import io.say.better.domain.solution.application.impl.SolutionService
import io.say.better.domain.solution.application.impl.SolutionSymbolService
import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateSolution
import io.say.better.domain.solution.ui.dto.SolutionResponse.SymbolList
import io.say.better.domain.symbol.application.impl.SymbolService
import io.say.better.storage.mysql.domain.entity.SolutionSymbol
import io.say.better.storage.mysql.domain.entity.Symbol
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Component
@RequiredArgsConstructor
class SolutionFacade (
        private val solutionService: SolutionService,
        private val solutionSymbolService: SolutionSymbolService,
        private val memberService: MemberService,
        private val symbolService: SymbolService,
        private val recommendClient: RecommendClient,
) {

    @Transactional(readOnly = true)
    fun recommendSymbol(name: String?): SymbolList {
        val recommend = recommendClient!!.recommend(name)
        val symbols = symbolService!!.getSymbols(recommend.symbols)
        return SolutionResponseConverter.toSymbolRecommend(name, symbols)
    }

    @Transactional(readOnly = true)
    fun searchSymbol(name: String?): SymbolList {
        val symbols = symbolService!!.getSymbols(name)
        return SolutionResponseConverter.toSymbolRecommend(name, symbols)
    }

    @Transactional
    fun createSolution(request: CreateSolution) {
        val member = memberService!!.currentMember()
        val newSolution = SolutionConverter.toSolution(request, member)
        val savedSolution = solutionService!!.createSolution(newSolution)

        val symbols: List<Symbol?> = symbolService!!.getSymbols(request.symbols)
        val solutionSymbols: List<SolutionSymbol?> = SolutionSymbolConverter.toSolutionSymbols(savedSolution, symbols)
        solutionSymbolService!!.createSolutionSymbols(solutionSymbols)

        savedSolution.onActivated()
    }
}
