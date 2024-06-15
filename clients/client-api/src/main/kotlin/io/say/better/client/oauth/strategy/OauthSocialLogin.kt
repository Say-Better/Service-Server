package io.say.better.client.oauth.strategy

import io.say.better.core.common.auth.info.OAuth2UserInfo
import io.say.better.core.common.constant.Provider

interface OauthSocialLogin {
    val provider: Provider

    fun verifyToken(idToken: String): OAuth2UserInfo
}
