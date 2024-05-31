package io.say.better.client.oauth.strategy.converter

import io.say.better.client.oauth.google.dto.GoogleOauth2Response
import io.say.better.client.oauth.strategy.dto.OauthResult

class OauthResultConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toGoogleUserInfo(
            response: GoogleOauth2Response.GoogleUserInfo
        ): OauthResult.GoogleUserInfo {
            return OauthResult.GoogleUserInfo(
                sub = response.sub,
                name = response.name,
                email = response.email
            )
        }
    }

}
