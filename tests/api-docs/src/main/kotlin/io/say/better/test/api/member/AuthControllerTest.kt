package io.say.better.test.api.member

import io.say.better.domain.member.application.AuthFacade
import io.say.better.domain.member.ui.AuthController
import io.say.better.global.common.code.status.SuccessStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(AuthController::class)
class AuthControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockBean val authFacade: AuthFacade
) {
    @Test
    @WithMockUser
    fun assignEducatorTest(): Unit {
        // When
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/auth/assign/educator")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess", true).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code", SuccessStatus.OK.code).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", SuccessStatus.OK.message).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist())
    }

    @Test
    @WithMockUser
    fun assignLearnerTest(): Unit {
        // When
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/auth/assign/learner")
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