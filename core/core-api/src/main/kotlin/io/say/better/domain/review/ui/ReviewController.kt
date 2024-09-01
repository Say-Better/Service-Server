package io.say.better.domain.review.ui

import io.say.better.core.common.response.ResponseDto
import io.say.better.domain.review.application.ReviewFacade
import io.say.better.domain.review.ui.dto.ReviewRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/review")
class ReviewController(
    private val reviewFacade: ReviewFacade,
) {
    @PostMapping
    fun submitReview(
        @RequestBody request: List<ReviewRequest.SubmitReview>,
    ): ResponseDto<Nothing?> {
        reviewFacade.submitReview(request)
        return ResponseDto.onSuccess(null)
    }
}
