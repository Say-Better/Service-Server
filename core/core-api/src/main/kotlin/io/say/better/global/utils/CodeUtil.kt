package io.say.better.global.utils

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component

@Slf4j
@RequiredArgsConstructor
@Component
class CodeUtil {
    fun createConnectCode(): String {
        val connectCodeLength = 6
        val useLetters = true
        val useNumbers = true

        return RandomStringUtils.random(connectCodeLength, useLetters, useNumbers)
    }
}
