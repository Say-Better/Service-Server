package io.say.better.global.common.constant

import com.fasterxml.jackson.annotation.JsonCreator
import org.apache.commons.lang3.EnumUtils

enum class AppType(
    val description: String,
) {
    EDUCATOR( "교육자"),
    LEARNER( "학습자"),

    ;

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun from(
            value: String
        ): AppType = value.let { EnumUtils.getEnumIgnoreCase(AppType::class.java, it.trim()) }

        fun fromString(value: String): AppType {
            return valueOf(value.uppercase())
        }
    }
}
