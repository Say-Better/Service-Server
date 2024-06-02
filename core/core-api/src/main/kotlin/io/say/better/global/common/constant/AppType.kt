package io.say.better.global.common.constant

import com.fasterxml.jackson.annotation.JsonValue

enum class AppType(
    private val lowerCase : String,
    val description: String,
) {
    EDUCATOR("educator", "교육자"),
    LEARNER("learner", "학습자"),

    ;

    companion object {
        fun fromString(value: String): AppType {
            return valueOf(value.uppercase())
        }
    }

    @JsonValue
    fun getAppType(): String {
        return lowerCase
    }
}
