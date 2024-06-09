package io.say.better.domain.member.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.core.enums.RoleType
import io.say.better.domain.member.application.AuthFacade
import io.say.better.test.api.RestDocsTest
import io.say.better.test.api.RestDocsUtils.requestPreprocessor
import io.say.better.test.api.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.security.test.context.support.WithMockUser

class AuthControllerTest : RestDocsTest() {
    private lateinit var authFacade: AuthFacade
    private lateinit var controller: AuthController

    @BeforeEach
    fun setUp() {
        authFacade = mockk()
        controller = AuthController(authFacade)
        mockMvc = mockController(controller)
    }

    @Test
    @DisplayName("유저에게 EDUCATOR 권한을 부여한다.")
    @WithMockUser
    fun assignEducatorTest() {
        every { authFacade.assignUserRole(RoleType.EDUCATOR) } returns Unit

        given()
            .contentType(ContentType.JSON)
            .post("/api/auth/assign/educator")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "assign-educator",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).ignored(),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("유저에게 LEARNER 권한을 부여한다.")
    @WithMockUser
    fun assignLearnerTest() {
        every { authFacade.assignUserRole(RoleType.LEARNER) } returns Unit

        given()
            .contentType(ContentType.JSON)
            .post("/api/auth/assign/learner")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "assign-learner",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).ignored(),
                    ),
                ),
            )
    }
}
