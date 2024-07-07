package io.say.better.domain.member.application.helper

import io.say.better.global.utils.AesEncryptUtil
import io.say.better.support.test.DevelopTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.encrypt.AesBytesEncryptor

@DevelopTest
class CommonLoginIdHelperTest {
    private lateinit var aesBytesEncryptor: AesBytesEncryptor
    private lateinit var aesEncryptUtil: AesEncryptUtil
    private lateinit var emailEncoderHelper: EmailEncoderHelper
    private lateinit var loginIdHelper: CommonLoginIdHelper

    @BeforeEach
    fun setUp() {
        aesBytesEncryptor = AesBytesEncryptor("password", "4b0febebd439db7ca77153cb254520c3")
        aesEncryptUtil = AesEncryptUtil(aesBytesEncryptor)
        emailEncoderHelper = EmailEncoderHelper(aesBytesEncryptor, aesEncryptUtil)
        loginIdHelper = CommonLoginIdHelper(emailEncoderHelper)
    }

    @Test
    @DisplayName("email을 통해 login id를 만드는데 성공한다.")
    fun getLoginIdFromEmailSuccess() {
        // given
        val email = "test@gmail.com"

        // when
        val loginId = loginIdHelper.getLoginId(email)

        // that
        assertThat(loginId)
            .isNotEmpty()
            .startsWith("common-")

        assertThat(loginId.substringAfter("common-"))
            .isNotEmpty()
            .isNotEqualTo(email)
    }

    @Test
    @DisplayName("login id를 통해 email을 찾는데 성공한다.")
    fun toEmailFromLoginId() {
        // given
        val email = "test@gmail.com"
        val loginId = loginIdHelper.getLoginId(email)

        // when
        val foundEmail = loginIdHelper.toEmail(loginId)

        // that
        assertThat(foundEmail).isEqualTo(email)
    }
}
