package io.say.better.client.oauth.strategy

import io.say.better.client.oauth.strategy.dto.OauthResult

fun interface OauthSocialLogin {
    fun verifyToken(idToken: String): OauthResult.GoogleUserInfo
}
