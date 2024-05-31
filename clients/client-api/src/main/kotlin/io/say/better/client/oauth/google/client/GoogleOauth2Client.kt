package io.say.better.client.oauth.google.client

import io.say.better.client.oauth.google.api.GoogleOauth2Api
import io.say.better.client.oauth.google.converter.GoogleOauth2ResultConverter
import io.say.better.client.oauth.google.dto.GoogleOauth2Result
import org.springframework.stereotype.Component

@Component
class GoogleOauth2Client(
    private val googleOauth2Api: GoogleOauth2Api,
) {
    fun verifyToken(idToken: String): GoogleOauth2Result.GoogleUserInfo {
        val response = googleOauth2Api.verifyToken(idToken)
        return GoogleOauth2ResultConverter.toGoogleUserInfo(response)
    }

}
