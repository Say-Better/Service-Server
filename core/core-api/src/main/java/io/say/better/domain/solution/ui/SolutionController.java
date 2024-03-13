package io.say.better.domain.solution.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.say.better.domain.solution.application.SolutionFacade;
import io.say.better.domain.solution.ui.dto.SolutionRequest;
import io.say.better.domain.solution.ui.dto.SolutionResponse;
import io.say.better.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Solution", description = "Solution API")
@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
public class SolutionController {

	private final SolutionFacade solutionFacade;

	@GetMapping("/symbol/recommend/{name}")
	public ResponseDto<SolutionResponse.SymbolList> recommendSymbol(@PathVariable String name) {
		return ResponseDto.onSuccess(solutionFacade.recommendSymbol(name));
	}

	@GetMapping("/symbol/search/{name}")
	public ResponseDto<SolutionResponse.SymbolList> searchSymbol(@PathVariable String name) {
		return ResponseDto.onSuccess(solutionFacade.searchSymbol(name));
	}

	@PostMapping("")
	public ResponseDto<Void> createSolution(
			@RequestBody SolutionRequest.CreateSolution request
	) {
		solutionFacade.createSolution(request);
		return ResponseDto.onSuccess(null);
	}

}
