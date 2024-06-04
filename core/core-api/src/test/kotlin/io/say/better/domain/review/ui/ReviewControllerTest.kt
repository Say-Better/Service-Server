package io.say.better.domain.review.ui

import io.say.better.domain.review.application.ReviewFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(ReviewController::class)
class ReviewControllerTest
    @Autowired
    constructor(
        private val mockMvc: MockMvc,
        @MockBean private val reviewFacade: ReviewFacade,
    )
