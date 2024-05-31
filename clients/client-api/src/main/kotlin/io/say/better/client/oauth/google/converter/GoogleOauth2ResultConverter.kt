package io.say.better.client.oauth.google.converter

import io.say.better.client.oauth.google.dto.GoogleOauth2Response
import io.say.better.client.oauth.google.dto.GoogleOauth2Result

class GoogleOauth2ResultConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toGoogleUserInfo(
            response: GoogleOauth2Response.GoogleUserInfo
        ): GoogleOauth2Result.GoogleUserInfo {
            return GoogleOauth2Result.GoogleUserInfo(
                sub = response.sub,
                name = response.name,
                email = response.email
            )
        }
    }

}
