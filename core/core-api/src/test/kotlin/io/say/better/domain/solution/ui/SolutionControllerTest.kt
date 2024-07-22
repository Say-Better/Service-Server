package io.say.better.domain.solution.ui

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.say.better.domain.solution.application.SolutionFacade
import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.domain.solution.ui.dto.SolutionResponse
import io.say.better.storage.mysql.domains.solution.type.ProgressState
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
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.security.test.context.support.WithMockUser

class SolutionControllerTest : RestControllerTest() {
    private lateinit var solutionFacade: SolutionFacade
    private lateinit var solutionController: SolutionController

    @BeforeEach
    fun setUp() {
        solutionFacade = mockk()
        solutionController = SolutionController(solutionFacade)
        mockMvc = mockController(solutionController)
    }

    @Test
    @DisplayName("symbol 추천 결과 리스트를 반환한다. 추천 결과가 없을 경우 빈 리스트를 반환한다.")
    @WithMockUser
    fun recommendSymbolTest() {
        val word = "testWord"
        val symbolInfo = SolutionResponse.SymbolInfo("testDescription1", "testImg1.jpg")
        val recommendList: List<SolutionResponse.SymbolInfo> = listOf(symbolInfo)

        every { solutionFacade.recommendSymbol(word) } returns SolutionResponse.SymbolList(word, recommendList)

        given()
            .contentType(ContentType.JSON)
            .get("/api/solution/symbol/recommend/{name}", word)
            .then()
            .status(HttpStatus.OK)
            .log()
            .all()
            .apply(
                document(
                    "success-recommend-symbol",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("name").description("상황 키워드"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("API 호출 결과"),
                        fieldWithPath("result.name").type(JsonFieldType.STRING).description("symbol 이름"),
                        fieldWithPath("result.symbols").type(JsonFieldType.ARRAY).description("symbol 추천 결과 리스트"),
                        fieldWithPath("result.symbols[].description")
                            .type(JsonFieldType.STRING)
                            .description("symbol 설명"),
                        fieldWithPath("result.symbols[].imageUrl")
                            .type(JsonFieldType.STRING)
                            .description("symbol 이미지 URL"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("symbol 이름을 탐색한 결과 리스트를 반환한다. 검색 결과가 없을 경우 빈 리스트를 반환한다.")
    @WithMockUser
    fun searchSymbolTest() {
        val symbolName = "testName"
        val symbolInfo = SolutionResponse.SymbolInfo("testDescription1", "testImg1.jpg")
        val searchList: List<SolutionResponse.SymbolInfo> = listOf(symbolInfo)
        every { solutionFacade.searchSymbol(symbolName) } returns SolutionResponse.SymbolList(symbolName, searchList)

        given()
            .params("name", symbolName)
            .contentType(ContentType.JSON)
            .get("/api/solution/symbol/search/{name}", symbolName)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-search-symbol",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("name").description("symbol 이름"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("API 호출 결과"),
                        fieldWithPath("result.name").type(JsonFieldType.STRING).description("symbol 이름"),
                        fieldWithPath("result.symbols").type(JsonFieldType.ARRAY).description("symbol 추천 결과 리스트"),
                        fieldWithPath("result.symbols[].description")
                            .type(JsonFieldType.STRING)
                            .description("symbol 설명"),
                        fieldWithPath("result.symbols[].imageUrl")
                            .type(JsonFieldType.STRING)
                            .description("symbol 이미지 URL"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("새로운 solution을 생성한다.")
    @WithMockUser
    fun createSolutionSuccessTest() {
        val request: SolutionRequest.CreateSolution =
            SolutionRequest.CreateSolution(
                learnerEmail = "learnerEmail@gmail.com",
                nowState = ProgressState.READY,
                educationGoal = "testEducationGoal",
                description = "testDescription",
                title = "testTitle",
                commOptTimes = 1,
                commOptCnt = 1,
            )

        every { solutionFacade.createSolution(request) } returns Unit

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/solution")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-create-solution",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("learnerEmail").type(JsonFieldType.STRING).description("학습자 이메일"),
                        fieldWithPath("nowState").type(JsonFieldType.STRING).description("솔루션의 현재 상태"),
                        fieldWithPath("educationGoal").type(JsonFieldType.STRING).description("학습 목표"),
                        fieldWithPath("description").type(JsonFieldType.STRING).description("솔루션 설명"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("솔루션 제목"),
                        fieldWithPath("commOptTimes").type(JsonFieldType.NUMBER).description("커뮤니케이션 기회 제공 시간"),
                        fieldWithPath("commOptCnt").type(JsonFieldType.NUMBER).description("커뮤니케이션 기회 제공 횟수"),
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

    @Test
    @DisplayName("솔루션을 시작한다.")
    @WithMockUser
    fun startSolutionTest() {
        val request: SolutionRequest.StartSolution =
            SolutionRequest.StartSolution(
                sessionOrder = 1,
                solutionId = 1,
                nowStep = "BASELINE",
                sessionGoal = "Test",
                sessionDesc = "Test",
            )
        val progressInfo: SolutionResponse.ProgressInfo =
            SolutionResponse.ProgressInfo(1)

        every { solutionFacade.startSolution(request) } returns progressInfo

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/solution/start")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-start-solution",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("sessionOrder").type(JsonFieldType.NUMBER).description("세션 순서"),
                        fieldWithPath("solutionId").type(JsonFieldType.NUMBER).description("솔루션 ID"),
                        fieldWithPath("nowStep").type(JsonFieldType.STRING).description("솔루션의 현재 단계"),
                        fieldWithPath("sessionGoal").type(JsonFieldType.STRING).description("세션 목표"),
                        fieldWithPath("sessionDesc").type(JsonFieldType.STRING).description("세션 설명"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("api 호출 성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.STRING).description("api 호출 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("api 호출 코드에 따른 메세지"),
                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("API 호출 결과"),
                        fieldWithPath("result.progressId").type(JsonFieldType.NUMBER).description("진행 ID"),
                    ),
                ),
            )
    }

    @Test
    @DisplayName("솔루션을 종료한다.")
    @WithMockUser
    fun endSolutionTest() {
        val createRecordSymbol: SolutionRequest.CreateRecordSymbol =
            SolutionRequest.CreateRecordSymbol(
                1,
                1,
                "timestamp",
            )

        val endSolution: SolutionRequest.EndSolution =
            SolutionRequest.EndSolution(
                1,
                1,
                1,
                listOf(createRecordSymbol),
                1,
            )

        val request: List<SolutionRequest.EndSolution> = listOf(endSolution)

        every { solutionFacade.endSolution(request) } returns Unit

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/api/solution/end")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "success-end-solution",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("[].solutionId").type(JsonFieldType.NUMBER).description("솔루션 ID"),
                        fieldWithPath("[].progressId").type(JsonFieldType.NUMBER).description("진행 ID"),
                        fieldWithPath("[].orderNum").type(JsonFieldType.NUMBER).description("순서"),
                        fieldWithPath("[].createRecordSymbols[].symbolId")
                            .type(JsonFieldType.NUMBER)
                            .description("심볼 ID"),
                        fieldWithPath("[].createRecordSymbols[].touchOrder")
                            .type(JsonFieldType.NUMBER)
                            .description("터치 순서"),
                        fieldWithPath("[].createRecordSymbols[].touchTime")
                            .type(JsonFieldType.STRING)
                            .description("터치 시간"),
                        fieldWithPath("[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 ID"),
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
