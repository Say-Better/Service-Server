package io.say.better.client.oauth.google.client

import io.say.better.client.oauth.google.api.GoogleOauth2Api
import io.say.better.client.oauth.strategy.converter.OauthResultConverter
import io.say.better.client.oauth.strategy.OauthSocialLogin
import io.say.better.client.oauth.strategy.dto.OauthResult
import org.springframework.stereotype.Component

@Component
class GoogleOauthClient(
    private val googleOauth2Api: GoogleOauth2Api,
) : OauthSocialLogin {
    override fun verifyToken(idToken: String): OauthResult.GoogleUserInfo {
        val response = googleOauth2Api.verifyToken(idToken)
        return OauthResultConverter.toGoogleUserInfo(response)
    }

}
