package io.say.better.domain.review.ui

import io.say.better.domain.review.application.ReviewFacade
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/review")
class ReviewController(
    private val reviewFacade: ReviewFacade,
)
