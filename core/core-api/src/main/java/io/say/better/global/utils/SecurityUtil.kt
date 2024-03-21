package io.say.better.global.utils

import lombok.NoArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Slf4j
@Component
@NoArgsConstructor
class SecurityUtil {
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
