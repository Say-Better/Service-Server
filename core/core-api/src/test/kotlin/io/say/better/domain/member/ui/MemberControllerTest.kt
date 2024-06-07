package io.say.better.domain.member.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.domain.member.application.MemberFacade
import io.say.better.domain.member.exception.MemberException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.common.code.status.SuccessStatus
import io.say.better.global.common.response.ResponseDto
import io.say.better.global.utils.CodeUtil
import io.say.better.test.api.RestDocsTest
import io.say.better.test.api.RestDocsUtils.requestPreprocessor
import io.say.better.test.api.RestDocsUtils.responsePreprocessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class MemberControllerTest : RestDocsTest() {
    private lateinit var memberFacade: MemberFacade
    private lateinit var memberController: MemberController

    @BeforeEach
    fun setUp() {
        memberFacade = mockk()
        memberController = MemberController(memberFacade)
        mockMvc = mockController(memberController)
    }

    @Test
    @DisplayName("code를 생성하여 반환한다.")
    @WithMockUser
    fun connectCodeTest() {
        every { memberFacade.createConnectCode() } returns "test23"

        given()
            .contentType(ContentType.JSON)
            .get("/api/member/connect/code")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-connect-code",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).description("생성된 학습자 연결코드"),
                    ),
                )
            )


    }

    @Test
    @DisplayName("유효한 코드일 경우 새로운 educator-learner 관계가 생성된다.")
    @WithMockUser
    fun connectSuccessTest() {
        every { memberFacade.connect(any()) } returns Unit

        given()
            .contentType(ContentType.JSON)
            .post("/api/member/connect/{code}", "testCode")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-connect",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("code").description("생성된 학습자 연결코드"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).ignored(),
                    ),
                )
            )
    }

    // TODO: 2024-06-07 실패 경우에 대한 테스트코드 작성 방식 확인
//    @Test
//    @DisplayName("유효하지 않은 코드일 경우 400 에러가 반환된다.")
//    @WithMockUser
//    fun connectFailTest() {
//        every { memberFacade.connect(any()) } returns Unit
//
//        given()
//            .contentType(ContentType.JSON)
//            .post("/api/member/connect/{code}", "testCode")
//            .then()
//            .status(ErrorStatus.CONNECT_CODE_NOT_VALID.httpStatus)
//            .extract()
//            .also {
//                document(
//                    "fail-connect",
//                    requestPreprocessor(),
//                    responsePreprocessor(),
//                    RequestDocumentation.pathParameters(
//                        RequestDocumentation.parameterWithName("code").description("생성된 학습자 연결코드"),
//                    ),
//                    responseFields(
//                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
//                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
//                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
//                        fieldWithPath("result").type(JsonFieldType.STRING).ignored(),
//                    ),
//                )
//            }
//    }
}
