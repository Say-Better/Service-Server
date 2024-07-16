package io.say.better.domain.chatting.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.domain.chatting.application.ChattingFacade
import io.say.better.domain.chatting.ui.dto.ChattingRequest
import io.say.better.domain.chatting.ui.dto.ChattingResponse
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
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.security.test.context.support.WithMockUser

class ChattingControllerTest : RestDocsTest() {
    private lateinit var chattingFacade: ChattingFacade
    private lateinit var chattingController: ChattingController

    @BeforeEach
    fun setUp() {
        chattingFacade = mockk()
        chattingController = ChattingController(chattingFacade)
        mockMvc = mockController(chattingController)
    }

    @Test
    @DisplayName("챗봇의 응답 결과가 반환된다.")
    @WithMockUser
    fun chattingTest() {
        every { chattingFacade.chat(any()) } returns ChattingResponse("firstResponse", "secondResponse")

        given()
            .contentType(ContentType.JSON)
            .body(ChattingRequest("requestSentence"))
            .get("/api/chatting")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-chatting",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("sentence").type(JsonFieldType.STRING).description("챗봇에게 전달하는 메시지"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.firstAnswer").type(JsonFieldType.STRING).description("챗봇 응답 값 (1)"),
                        fieldWithPath("result.secondAnswer").type(JsonFieldType.STRING).description("챗봇 응답 값 (2)"),
                    ),
                ),
            )
    }
}
