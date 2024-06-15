package io.say.better.core.common.auth.info

interface OAuth2UserInfo {
    val providerId: String

    val provider: String

    val email: String

    val name: String
}
