package io.say.better.test.api.member

import io.say.better.domain.member.application.MemberFacade
import io.say.better.domain.member.exception.MemberException
import io.say.better.domain.member.ui.MemberController
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.common.code.status.SuccessStatus
import io.say.better.global.utils.CodeUtil
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MemberController::class)
class MemberControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockBean private val memberFacade: MemberFacade,
    @MockBean private val codeUtil: CodeUtil
) {
    @Test
    @WithMockUser
    fun connectCodeTest(): Unit {
        // Given
        val code: String = codeUtil.createConnectCode()
        BDDMockito.given(memberFacade.createConnectCode()).willReturn(code)

        // When
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/member/connect/code")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess", true).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code", SuccessStatus.OK.code).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",  SuccessStatus.OK.message).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist())
    }

    @Test
    @WithMockUser
    fun connectSuccessTest(): Unit {
        // Given
        val key = "testKey"
        BDDMockito.doNothing().`when`(memberFacade).connect(key)

        // When
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/member/connect/{code}", key)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess", true).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code", ErrorStatus.CONNECT_CODE_NOT_VALID.code).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", ErrorStatus.CONNECT_CODE_NOT_VALID.message).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist())
    }

    @Test
    @WithMockUser
    fun connectFailTest(): Unit {
        // Given
        val key = "testKey"
        BDDMockito.given(memberFacade.connect(key)).willThrow(MemberException(ErrorStatus.CONNECT_CODE_NOT_VALID))

        // When
        val actions: ResultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/member/connect/{code}", key)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess", true).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code", ErrorStatus.CONNECT_CODE_NOT_VALID.code).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", ErrorStatus.CONNECT_CODE_NOT_VALID.message).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist())
            .andDo(MockMvcResultHandlers.print())
    }
}
