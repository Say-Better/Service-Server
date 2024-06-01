package io.say.better.client.oauth.strategy

import io.say.better.core.enums.Provider
import io.say.better.core.enums.auth.info.OAuth2UserInfo

interface OauthSocialLogin {
    val provider: Provider

    fun verifyToken(idToken: String): OAuth2UserInfo
}
