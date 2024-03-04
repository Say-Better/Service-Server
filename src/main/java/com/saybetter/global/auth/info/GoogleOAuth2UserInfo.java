package com.saybetter.global.auth.info;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes;

	@Override
	public String getProviderId() {
		return (String)attributes.get("sub");
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}
}
