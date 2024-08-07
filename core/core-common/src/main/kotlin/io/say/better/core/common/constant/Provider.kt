package io.say.better.core.common.constant

import com.fasterxml.jackson.annotation.JsonCreator
import org.apache.commons.lang3.EnumUtils
import java.util.*
import java.util.stream.Stream

enum class Provider(
    val description: String,
) {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao"),
    FACEBOOK("facebook"),
    COMMON("common"),
    ;

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun from(value: String): Provider = value.let { EnumUtils.getEnumIgnoreCase(Provider::class.java, it.trim()) }

        fun find(description: String): Optional<Provider> =
            Stream
                .of(*entries.toTypedArray())
                .filter { it.description == description }
                .findFirst()
    }
}
