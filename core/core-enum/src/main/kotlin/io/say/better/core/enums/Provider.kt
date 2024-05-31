package io.say.better.core.enums

import com.fasterxml.jackson.annotation.JsonValue
import java.util.Optional
import java.util.stream.Stream

enum class Provider(
    val description: String,
) {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao"),
    FACEBOOK("facebook"),
    ;

    companion object {
        fun find(description: String): Optional<Provider> {
            return Stream.of(*entries.toTypedArray())
                .filter { it.description == description }
                .findFirst()
        }
    }

    @JsonValue
    fun getProvider(): String {
        return description
    }
}
