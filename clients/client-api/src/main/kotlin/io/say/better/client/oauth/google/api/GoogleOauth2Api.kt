package io.say.better.client.oauth.google.api

import io.say.better.client.oauth.google.config.GoogleOauth2FeignConfiguration
import io.say.better.core.enums.auth.info.GoogleOAuth2UserInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "googleOauth2Client",
    url = "https://www.googleapis.com/oauth2/v3",
    configuration = [GoogleOauth2FeignConfiguration::class],
)
internal fun interface GoogleOauth2Api {
    @GetMapping("/tokeninfo")
    fun verifyToken(
        @RequestParam("id_token") idToken: String,
    ): GoogleOAuth2UserInfo
}
