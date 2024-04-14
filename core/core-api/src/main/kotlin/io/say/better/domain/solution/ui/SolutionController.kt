package io.say.better.domain.solution.ui

import io.say.better.domain.solution.application.SolutionFacade
import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateSolution
import io.say.better.domain.solution.ui.dto.SolutionResponse.SymbolList
import io.say.better.global.common.response.ResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
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
}
