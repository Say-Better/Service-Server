package io.say.better.core.common.auth.info

import io.say.better.core.common.constant.Provider

data class GoogleOAuth2UserInfo(
    val attributes: Map<String, Any>?,
) : OAuth2UserInfo {
    override val providerId: String
        get() = (attributes!!["sub"] as String)

    override val provider: String
        get() = Provider.GOOGLE.name.lowercase()

    override val email: String
        get() = attributes!!["email"] as String

    override val name: String
        get() = attributes!!["name"] as String
}
