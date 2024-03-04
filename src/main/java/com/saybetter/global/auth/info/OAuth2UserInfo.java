package com.saybetter.global.auth.info;

public interface OAuth2UserInfo {
	String getProviderId();

	String getProvider();

	String getEmail();

	String getName();
}
