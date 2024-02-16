package com.saybetter.domain.review.ui;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saybetter.domain.review.application.ReviewFacade;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Review", description = "Review API")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewFacade reviewFacade;

}
