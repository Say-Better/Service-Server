package io.say.better.client.oauth.google.client

import io.say.better.client.oauth.google.api.GoogleOauth2Api
import io.say.better.client.oauth.strategy.OauthSocialLogin
import io.say.better.core.common.auth.info.GoogleOAuth2UserInfo
import io.say.better.core.common.constant.Provider
import org.springframework.stereotype.Component

@Component
class GoogleOauthClient internal constructor(
    private val googleOauth2Api: GoogleOauth2Api,
) : OauthSocialLogin {
    override val provider: Provider
        get() = Provider.GOOGLE

    override fun verifyToken(idToken: String): GoogleOAuth2UserInfo = googleOauth2Api.verifyToken(idToken)
}
