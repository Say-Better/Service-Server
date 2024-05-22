package io.say.better.global.utils

import io.say.better.global.config.logger.logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityUtil constructor() {
    private val log = logger()

    fun clearSecurityContext() {
        SecurityContextHolder.clearContext()
    }

    val currentUserEmail: String
        get() {
            val authentication =
                SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
            return authentication.name
        }

    val userAuthorities: Collection<GrantedAuthority>
        get() {
            val authentication =
                SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
            return authentication.authorities
        }
}
