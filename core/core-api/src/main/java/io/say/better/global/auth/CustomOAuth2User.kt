package io.say.better.global.auth

import io.say.better.core.enums.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User


data class CustomOAuth2User(
    val authorities: Collection<GrantedAuthority?>?,
    val attributes: Map<String?, Any?>?,
    val nameAttributeKey: String?,
    val email: String,
    val role: RoleType
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if ((other == null) || (javaClass != other.javaClass) || super.equals(other).not()) return false

        val that = other as CustomOAuth2User

        if (email != that.email) return false
        return role == that.role
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }
}
