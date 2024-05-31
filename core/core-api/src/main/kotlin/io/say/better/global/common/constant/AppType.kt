package io.say.better.global.common.constant

import com.fasterxml.jackson.annotation.JsonValue

enum class AppType(
    val description: String,
) {
    EDUCATOR("교육자"),
    LEARNER("학습자"),

    ;

    companion object {
        fun fromString(value: String): AppType {
            return valueOf(value.uppercase())
        }
    }

    @JsonValue
    fun getAppType(): String {
        return description
    }
}
