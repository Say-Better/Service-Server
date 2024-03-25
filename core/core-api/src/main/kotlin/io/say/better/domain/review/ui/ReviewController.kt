package io.say.better.domain.review.ui

import io.say.better.domain.review.application.ReviewFacade
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Review", description = "Review API")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
class ReviewController (
    private val reviewFacade: ReviewFacade,
)
