package io.say.better.domain.member.application.helper

import io.say.better.global.utils.AesEncryptUtil
import io.say.better.support.test.DevelopTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.encrypt.AesBytesEncryptor

@DevelopTest
class EmailEncoderHelperTest {
    private lateinit var aesBytesEncryptor: AesBytesEncryptor
    private lateinit var aesEncryptUtil: AesEncryptUtil
    private lateinit var emailEncoderHelper: EmailEncoderHelper

    @BeforeEach
    fun setUp() {
        aesBytesEncryptor = AesBytesEncryptor("password", "4b0febebd439db7ca77153cb254520c3")
        aesEncryptUtil = AesEncryptUtil(aesBytesEncryptor)
        emailEncoderHelper = EmailEncoderHelper(aesBytesEncryptor, aesEncryptUtil)
    }

    @Test
    @DisplayName("Email을 AES 알고리즘으로 암호화한다.")
    fun encoder() {
        // given
        val email = "test@gmail.com"

        // when
        val encoded = emailEncoderHelper.encoder(email)

        // that
        assertThat(encoded)
            .isNotNull()
            .isNotEmpty()
            .isNotEqualTo(email)
    }

    @Test
    @DisplayName("AES 알고리즘으로 암호화된 Email을 복호화한다.")
    fun decoder() {
        // given
        val email = "test@gmail.com"
        val encoded = emailEncoderHelper.encoder(email)

        // when
        val decoded = emailEncoderHelper.decoder(encoded)

        // that
        assertThat(decoded).isEqualTo(email)
    }
}
