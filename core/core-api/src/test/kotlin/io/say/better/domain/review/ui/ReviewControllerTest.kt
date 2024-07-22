package io.say.better.domain.review.ui

import io.mockk.mockk
import io.say.better.domain.review.application.ReviewFacade
import io.say.better.support.test.docs.RestControllerTest
import org.junit.jupiter.api.BeforeEach

class ReviewControllerTest : RestControllerTest() {
    private lateinit var reviewFacade: ReviewFacade
    private lateinit var reviewController: ReviewController

    @BeforeEach
    fun setup() {
        reviewFacade = mockk()
        reviewController = ReviewController(reviewFacade)
        mockMvc = mockController(reviewController)
    }
}
