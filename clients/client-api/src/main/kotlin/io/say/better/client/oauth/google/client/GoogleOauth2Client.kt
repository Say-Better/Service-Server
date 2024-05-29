package io.say.better.client.oauth.google.client

import io.say.better.client.oauth.google.api.GoogleOauth2Api
import org.springframework.stereotype.Component

@Component
class GoogleOauth2Client(
    private val googleOauth2Api: GoogleOauth2Api
) {

}
