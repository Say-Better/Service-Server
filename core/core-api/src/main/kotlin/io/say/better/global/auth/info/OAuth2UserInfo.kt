package io.say.better.global.auth.info

interface OAuth2UserInfo {
    val providerId: String

    val provider: String

    val email: String

    val name: String
}
