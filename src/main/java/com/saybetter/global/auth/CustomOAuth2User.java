package com.saybetter.global.auth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.saybetter.global.common.constant.RoleType;

import lombok.Getter;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

	private final String email;
	private final RoleType role;

	/**
	 * Constructs a {@code DefaultOAuth2User} using the provided parameters.
	 *
	 * @param authorities      the authorities granted to the user
	 * @param attributes       the attributes about the user
	 * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
	 *                         {@link #getAttributes()}
	 */
	public CustomOAuth2User(
			Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> attributes,
			String nameAttributeKey,
			String email,
			RoleType role
	) {
		super(authorities, attributes, nameAttributeKey);
		this.email = email;
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}

		CustomOAuth2User that = (CustomOAuth2User)obj;

		if (!email.equals(that.email)) {
			return false;
		}
		return role == that.role;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + role.hashCode();
		return result;
	}
}
