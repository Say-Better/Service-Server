package io.say.better.client.oauth.google.client

import io.say.better.client.oauth.google.api.GoogleOauth2Api
import io.say.better.client.oauth.strategy.OauthSocialLogin
import io.say.better.core.enums.Provider
import io.say.better.core.enums.auth.info.GoogleOAuth2UserInfo
import org.springframework.stereotype.Component

@Component
class GoogleOauthClient(
    private val googleOauth2Api: GoogleOauth2Api,
) : OauthSocialLogin {
    override val provider: Provider
        get() = Provider.GOOGLE

    override fun verifyToken(idToken: String): GoogleOAuth2UserInfo {
        return googleOauth2Api.verifyToken(idToken)
    }
}
