package io.say.better.test.api.solution

import com.google.gson.Gson
import io.say.better.domain.solution.application.SolutionFacade
import io.say.better.domain.solution.ui.SolutionController
import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.domain.solution.ui.dto.SolutionResponse
import io.say.better.global.common.code.status.SuccessStatus
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(SolutionController::class)
class SolutionControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockBean private val solutionFacade: SolutionFacade
) {
    @Test
    @WithMockUser
    fun searchSymbolTest(): Unit {
        // Given
        val symbolName = "testName"
        val symbolList = SolutionResponse.SymbolList(symbolName, null)
        BDDMockito.given(solutionFacade.searchSymbol(symbolName)).willReturn(symbolList)

        // When
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/solution/symbol/search/{name}", symbolName)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess", true).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code", SuccessStatus.OK.code).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", SuccessStatus.OK.message).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result", List::class).exists())
    }

    @Test
    @WithMockUser
    fun createSolutionTest(): Unit {
        // Given
        val request: SolutionRequest.CreateSolution = SolutionRequest.CreateSolution("testTitle")
        BDDMockito.doNothing().`when`(solutionFacade).createSolution(request)

        // When
        val converter = Gson()
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/solution")
                .content(converter.toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess", true).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code", SuccessStatus.OK.code).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", SuccessStatus.OK.message).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist())
    }
}
