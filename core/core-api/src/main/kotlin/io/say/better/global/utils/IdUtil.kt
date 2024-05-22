package io.say.better.global.utils

import io.say.better.global.config.logger.logger
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class IdUtil constructor() {
    private val log = logger()

    fun getUUID(): UUID {
        return UUID.randomUUID()
    }

    fun toUUID(uuidString: String): UUID {
        return UUID.fromString(uuidString)
    }
}
