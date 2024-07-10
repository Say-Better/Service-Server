package io.say.better.domain.member.application.helper

import com.nimbusds.jose.util.StandardCharset
import io.say.better.global.utils.AesEncryptUtil
import org.springframework.security.crypto.encrypt.AesBytesEncryptor
import org.springframework.stereotype.Component

@Component
class EmailEncoderHelper(
    private val aesBytesEncryptor: AesBytesEncryptor,
    private val aesEncryptUtil: AesEncryptUtil,
) {
    fun encoder(email: String): String {
        val encrypt = aesBytesEncryptor.encrypt(email.toByteArray(StandardCharset.UTF_8))
        return aesEncryptUtil.byteArrayToString(encrypt)
    }

    fun decoder(encryptString: String): String {
        val bytes = aesEncryptUtil.stringToByteArray(encryptString)
        val decrypt = aesBytesEncryptor.decrypt(bytes)
        return String(decrypt, StandardCharset.UTF_8)
    }
}
