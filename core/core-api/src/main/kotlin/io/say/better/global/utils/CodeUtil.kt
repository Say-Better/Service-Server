package io.say.better.global.utils

import io.say.better.global.config.logger.logger
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component

@Component
class CodeUtil constructor() {
    private val log = logger()

    fun createConnectCode(): String {
        val connectCodeLength = 6
        val useLetters = true
        val useNumbers = true

        return RandomStringUtils.random(connectCodeLength, useLetters, useNumbers)
    }
}
