package io.say.better.domain.member.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.core.common.constant.AppType
import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.domain.member.application.AuthFacade
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.domain.member.ui.dto.AuthResponse
import io.say.better.support.test.docs.RestControllerTest
import io.say.better.support.util.RestDocsUtils.requestPreprocessor
import io.say.better.support.util.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.security.test.context.support.WithMockUser

class AuthControllerTest : RestControllerTest() {
    private lateinit var authFacade: AuthFacade
    private lateinit var controller: AuthController

    @BeforeEach
    fun setUp() {
        authFacade = mockk()
        controller = AuthController(authFacade)
        mockMvc = mockController(controller)
    }

    @Test
    @DisplayName("교육자앱에서 소셜 로그인을 성공한다.")
    fun educatorSocialLoginSuccess() {
        // given
        val appType = AppType.EDUCATOR
        val provider = Provider.GOOGLE
        val request = AuthRequest.LoginDTO("identityToken")
        val response =
            AuthResponse.LoginDTO(
                1L,
                "accessToken",
                "refreshToken",
                true,
            )

        every { authFacade.login(appType, provider, request) } returns response

        // when
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/auth/login/{appType}/{socialType}", appType.name, provider.name)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "educator-login",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("appType").description("앱 타입"),
                        RequestDocumentation.parameterWithName("socialType").description("소셜 타입"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.memberId").type(JsonFieldType.NUMBER).description("회원 아이디"),
                        fieldWithPath("result.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                        fieldWithPath("result.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰"),
                        fieldWithPath(
                            "result.needMemberInfo",
                        ).type(JsonFieldType.BOOLEAN).description("회원 정보 입력 필요 여부"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("학습자앱에서 소셜 로그인을 성공한다.")
    fun learnerSocialLoginSuccess() {
        // given
        val appType = AppType.LEARNER
        val provider = Provider.GOOGLE
        val request = AuthRequest.LoginDTO("identityToken")
        val response =
            AuthResponse.LoginDTO(
                1L,
                "accessToken",
                "refreshToken",
                true,
            )

        every { authFacade.login(appType, provider, request) } returns response

        // when
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/auth/login/{appType}/{socialType}", appType.name, provider.name)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "learner-login",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("appType").description("앱 타입"),
                        RequestDocumentation.parameterWithName("socialType").description("소셜 타입"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.memberId").type(JsonFieldType.NUMBER).description("회원 아이디"),
                        fieldWithPath("result.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                        fieldWithPath("result.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰"),
                        fieldWithPath(
                            "result.needMemberInfo",
                        ).type(JsonFieldType.BOOLEAN).description("회원 정보 입력 필요 여부"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("교육자앱에서 일반 로그인을 성공한다.")
    fun educatorCommonLoginSuccess() {
        // given
        val appType = AppType.EDUCATOR
        val request = AuthRequest.CommonLoginDTO("email", "password")
        val response =
            AuthResponse.LoginDTO(
                1L,
                "accessToken",
                "refreshToken",
                true,
            )

        every { authFacade.login(appType, request) } returns response

        // when
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/auth/login/{appType}/common", appType.name)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "educator-common-login",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("appType").description("앱 타입"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.memberId").type(JsonFieldType.NUMBER).description("회원 아이디"),
                        fieldWithPath("result.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                        fieldWithPath("result.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰"),
                        fieldWithPath(
                            "result.needMemberInfo",
                        ).type(JsonFieldType.BOOLEAN).description("회원 정보 입력 필요 여부"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("학습자앱에서 일반 로그인을 성공한다.")
    fun learnerCommonLoginSuccess() {
        // given
        val appType = AppType.LEARNER
        val request = AuthRequest.CommonLoginDTO("email", "password")
        val response =
            AuthResponse.LoginDTO(
                1L,
                "accessToken",
                "refreshToken",
                true,
            )

        every { authFacade.login(appType, request) } returns response

        // when
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/auth/login/{appType}/common", appType.name)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "learner-common-login",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("appType").description("앱 타입"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result.memberId").type(JsonFieldType.NUMBER).description("회원 아이디"),
                        fieldWithPath("result.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                        fieldWithPath("result.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰"),
                        fieldWithPath(
                            "result.needMemberInfo",
                        ).type(JsonFieldType.BOOLEAN).description("회원 정보 입력 필요 여부"),
                    ),
                ),
            )
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
