package io.say.better.client.oauth.strategy.dto

class OauthResult private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class GoogleUserInfo(
        val sub : String,
        val name : String,
        val email : String,
    )
}
