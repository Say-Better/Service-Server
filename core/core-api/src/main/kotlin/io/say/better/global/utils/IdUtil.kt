package io.say.better.global.utils

import lombok.NoArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.util.UUID

@Slf4j
@Component
@NoArgsConstructor
class IdUtil {
    fun getUUID(): UUID {
        return UUID.randomUUID()
    }

    fun toUUID(uuidString: String): UUID {
        return UUID.fromString(uuidString)
    }
}
