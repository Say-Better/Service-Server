package io.say.better.global.common.constant

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
}
