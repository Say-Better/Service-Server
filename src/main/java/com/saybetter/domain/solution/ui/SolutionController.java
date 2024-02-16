package com.saybetter.domain.solution.ui;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Solution", description = "Solution API")
@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
public class SolutionController {

}
