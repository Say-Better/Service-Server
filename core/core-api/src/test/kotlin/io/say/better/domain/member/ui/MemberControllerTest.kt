package io.say.better.domain.member.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.domain.member.application.MemberFacade
import io.say.better.domain.member.ui.dto.MemberRequest
import io.say.better.domain.member.ui.dto.MemberResponse
import io.say.better.storage.mysql.domains.account.type.Gender
import io.say.better.support.test.docs.RestControllerTest
import io.say.better.support.util.RestDocsUtils.requestPreprocessor
import io.say.better.support.util.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.security.test.context.support.WithMockUser

class MemberControllerTest : RestControllerTest() {
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
                    "get-connect-code",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).description("생성된 학습자 연결코드"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("유효한 코드일 경우 새로운 educator-learner 관계가 생성된다.")
    @WithMockUser
    fun connectSuccessTest() {
        every { memberFacade.connect(any()) } returns true

        given()
            .contentType(ContentType.JSON)
            .post("/api/member/connect/{code}", "testCode")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "post-connect-code",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("code").description("생성된 학습자 연결코드"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result")
                            .type(JsonFieldType.BOOLEAN)
                            .description("코드 검사 및 educator-learner 관계 성립 성공 여부"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("Educator 정보를 반환한다.")
    @WithMockUser
    fun getEducatorInfoTest() {
        every { memberFacade.getEducatorInfo() } returns MemberResponse.EducatorDTO("testName", "testUrl")

        given()
            .contentType(ContentType.JSON)
            .get("/api/member/educator/info")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "get-educator-info",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("result.imgUrl").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("Learner 정보를 반환한다.")
    @WithMockUser
    fun getLearnerInfoTest() {
        every {
            memberFacade.getLearnerInfo()
        } returns MemberResponse.LearnerDTO("testName", 10, Gender.MALE, "testUrl")

        given()
            .contentType(ContentType.JSON)
            .get("/api/member/learner/info")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "get-learner-info",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("result.age").type(JsonFieldType.NUMBER).description("나이"),
                        fieldWithPath("result.gender").type(JsonFieldType.STRING).description("성별"),
                        fieldWithPath("result.imgUrl").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("Educator 초기 정보를 저장한다.")
    @WithMockUser
    fun postEducatorInfoTest() {
        val mockMultiPartFile =
            MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test".byteInputStream())

        every {
            memberFacade.postEducatorInfo(
                any(),
                any(),
            )
        } returns Unit

        given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .queryParam("name", "testName")
            .multiPart("file", "text.txt", mockMultiPartFile.bytes)
            .post("/api/member/educator/info")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "post-educator-info",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("Learner 초기 정보를 저장한다.")
    fun postLearnerInfoTest() {
        val mockMultiPartFile = MockMultipartFile("file", "test.txt", "text/plain", "Test".byteInputStream())

        every {
            memberFacade.postLearnerInfo(
                any(),
                any(),
            )
        } returns Unit

        val request =
            MemberRequest.LearnerInitialInfoDTO(
                "학습자",
                "2014.01.02",
                "M",
            )

        given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("file", "text.txt", mockMultiPartFile.bytes)
            .multiPart("dto", request, MediaType.APPLICATION_JSON_VALUE)
            .post("/api/member/learner/info")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "post-learner-info",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestPartFields(
                        "dto",
                        fieldWithPath("name").type(JsonFieldType.STRING).description("학습자 이름"),
                        fieldWithPath("birthday").type(JsonFieldType.STRING).description("생일(형식: yyyy.MM.dd"),
                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별(M: 남성, F: 여성)"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    ),
                ),
            )
    }
}
