package io.say.better.domain.review.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.domain.review.application.ReviewFacade
import io.say.better.domain.review.ui.dto.ReviewRequest
import io.say.better.support.test.docs.RestControllerTest
import io.say.better.support.util.RestDocsUtils.requestPreprocessor
import io.say.better.support.util.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.security.test.context.support.WithMockUser

class ReviewControllerTest : RestControllerTest() {
    private lateinit var reviewFacade: ReviewFacade
    private lateinit var reviewController: ReviewController

    @BeforeEach
    fun setup() {
        reviewFacade = mockk()
        reviewController = ReviewController(reviewFacade)
        mockMvc = mockController(reviewController)
    }

    @Test
    @DisplayName("Review 결과를 제출한다")
    @WithMockUser
    fun submitReviewTest() {
        val reviews =
            listOf(
                ReviewRequest.SubmitReview(1L, "TRUE"),
                ReviewRequest.SubmitReview(2L, "TRUE"),
                ReviewRequest.SubmitReview(3L, "TRUE"),
            )

        every { reviewFacade.submitReview(reviews) } returns Unit

        given()
            .contentType(ContentType.JSON)
            .body(reviews)
            .post("/api/review")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "post-review-result",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        listOf(
                            fieldWithPath("[]").type(JsonFieldType.ARRAY).description("List"),
                            fieldWithPath("[].recordId").type(JsonFieldType.NUMBER).description("레코드 ID"),
                            fieldWithPath("[].reactionType")
                                .type(JsonFieldType.STRING)
                                .description("리엑션 타입 (TRUE: 정반응 / FALSE: 오반응 / NONE: 없음"),
                        ),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("API 호출 결과").ignored(),
                    ),
                ),
            )
    }
}
