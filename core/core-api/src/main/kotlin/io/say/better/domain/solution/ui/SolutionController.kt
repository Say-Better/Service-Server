package io.say.better.domain.solution.ui

import io.say.better.core.common.response.ResponseDto
import io.say.better.domain.solution.application.SolutionFacade
import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateSolution
import io.say.better.domain.solution.ui.dto.SolutionRequest.EndSolution
import io.say.better.domain.solution.ui.dto.SolutionRequest.StartSolution
import io.say.better.domain.solution.ui.dto.SolutionResponse.ProgressInfo
import io.say.better.domain.solution.ui.dto.SolutionResponse.SymbolList
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Solution", description = "Solution API")
@RestController
@RequestMapping("/api/solution")
class SolutionController(
    private val solutionFacade: SolutionFacade,
) {
    @GetMapping("/symbol/recommend/{name}")
    fun recommendSymbol(
        @PathVariable(name = "name") name: String,
    ): ResponseDto<SymbolList> = ResponseDto.onSuccess(solutionFacade.recommendSymbol(name))

    @GetMapping("/symbol/search/{name}")
    fun searchSymbol(
        @PathVariable(name = "name") name: String,
    ): ResponseDto<SymbolList> = ResponseDto.onSuccess(solutionFacade.searchSymbol(name))

    @PostMapping("")
    fun createSolution(
        @RequestBody request: CreateSolution,
    ): ResponseDto<Nothing?> {
        solutionFacade.createSolution(request)
        return ResponseDto.onSuccess(null)
    }

    @PostMapping("/start")
    fun startSolution(
        @RequestBody request: StartSolution,
    ): ResponseDto<ProgressInfo> = ResponseDto.onSuccess(solutionFacade.startSolution(request))

    @PostMapping("/end")
    fun endSolution(
        @RequestBody request: List<EndSolution>,
    ): ResponseDto<Nothing?> {
        solutionFacade.endSolution(request)
        return ResponseDto.onSuccess(null)
    }
}
