package io.say.better.global.utils

import org.springframework.security.crypto.encrypt.AesBytesEncryptor
import org.springframework.stereotype.Component

@Component
class AesEncryptUtil(
    private val encryptor: AesBytesEncryptor,
) {
    fun byteArrayToString(bytes: ByteArray): String = bytes.joinToString(separator = " ") { it.toString() }

    fun stringToByteArray(byteString: String): ByteArray =
        byteString
            .split("\\s".toRegex())
            .map {
                it.toByte()
            }.toByteArray()
}
