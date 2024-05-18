package io.say.better.domain.solution.ui

import io.say.better.domain.solution.application.SolutionFacade
import io.say.better.domain.solution.ui.dto.SolutionRequest.EndSolution
import io.say.better.domain.solution.ui.dto.SolutionRequest.StartSolution
import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateSolution
import io.say.better.domain.solution.ui.dto.SolutionResponse.ProgressInfo
import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.domain.solution.ui.dto.SolutionResponse
import io.say.better.domain.solution.ui.dto.SolutionResponse.SymbolList
import io.say.better.global.common.response.ResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*

@Tag(name = "Solution", description = "Solution API")
@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
class SolutionController(
    private val solutionFacade: SolutionFacade
) {
    @GetMapping("/symbol/recommend/{name}")
    fun recommendSymbol(@PathVariable(name = "name") name: String): ResponseDto<SymbolList> {
        return ResponseDto.onSuccess(solutionFacade!!.recommendSymbol(name))
    }

    @GetMapping("/symbol/search/{name}")
    fun searchSymbol(@PathVariable(name = "name") name: String): ResponseDto<SymbolList> {
        return ResponseDto.onSuccess(solutionFacade!!.searchSymbol(name))
    }

    @PostMapping("")
    fun createSolution(
            @RequestBody request: CreateSolution?
    ): ResponseDto<Void?> {
        solutionFacade!!.createSolution(request!!)
        return ResponseDto.onSuccess(null)
    }

    @PostMapping("/start")
    fun startSolution(
        @RequestBody request: StartSolution?
    ): ResponseDto<ProgressInfo> {

        return ResponseDto.onSuccess(solutionFacade.startSolution(request!!))
    }

    @PostMapping("/end")
    fun endSolution(
        @RequestBody request: List<EndSolution>?
    ): ResponseDto<Void?> {
        solutionFacade.endSolution(request!!)
        return ResponseDto.onSuccess(null)
    }
}
