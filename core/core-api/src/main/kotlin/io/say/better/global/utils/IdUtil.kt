package io.say.better.global.utils

import io.say.better.core.common.utils.logger
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class IdUtil constructor() {
    private val log = logger()

    fun getUUID(): UUID = UUID.randomUUID()

    fun toUUID(uuidString: String): UUID = UUID.fromString(uuidString)
}
