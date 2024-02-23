package com.saybetter.global.utils;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public class SecurityUtil {

	public void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	public String getCurrentUserEmail() {
		UsernamePasswordAuthenticationToken authentication =
				(UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public Collection<GrantedAuthority> getUserAuthorities() {
		UsernamePasswordAuthenticationToken authentication =
				(UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities();
	}

}
