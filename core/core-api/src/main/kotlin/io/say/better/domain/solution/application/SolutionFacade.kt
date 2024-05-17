package io.say.better.domain.solution.application

import io.say.better.client.symbol.api.RecommendClient
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.solution.application.converter.SolutionConverter
import io.say.better.domain.solution.application.converter.SolutionResponseConverter
import io.say.better.domain.solution.application.impl.SolutionService
import io.say.better.domain.solution.application.impl.SolutionSymbolService
import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.domain.symbol.application.impl.SymbolService
import io.say.better.global.advice.Tx
import io.say.better.global.config.logger.logger
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
class SolutionFacade (
    private val solutionService: SolutionService,
    private val solutionSymbolService: SolutionSymbolService,
    private val memberService: MemberService,
    private val symbolService: SymbolService,
    private val recommendClient: RecommendClient,
) {

    private val log = logger()

    fun recommendSymbol(name: String) = Tx.readable {
        val recommend = recommendClient.recommend(name)
        val symbols = symbolService.getSymbols(recommend.symbols)
        return@readable SolutionResponseConverter.toSymbolRecommend(name, symbols)
    }

    fun searchSymbol(name: String) = Tx.readable {
        val symbols = symbolService.getSymbols(name)
        return@readable SolutionResponseConverter.toSymbolRecommend(name, symbols)
    }

    fun createSolution(request: SolutionRequest.CreateSolution) = Tx.writeable {
        val member = memberService.currentMember() as Educator
        val learner = memberService.getMember(request.learnerEmail) as Learner
        val newSolution = SolutionConverter.toSolution(request, member, learner)
        val savedSolution = solutionService.createSolution(newSolution)

        savedSolution.onActivated()
    }
}
