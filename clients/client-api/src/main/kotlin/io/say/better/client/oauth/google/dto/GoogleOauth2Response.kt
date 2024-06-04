package io.say.better.client.oauth.google.dto

class GoogleOauth2Response private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class GoogleUserInfo(
        val iss: String,
        val azp: String,
        val aud: String,
        // user id
        val sub: String,
        val email: String,
        val email_verified: Boolean,
        val at_hash: String,
        val name: String,
        val picture: String,
        val given_name: String,
        val family_name: String,
        val locale: String,
    )
}
