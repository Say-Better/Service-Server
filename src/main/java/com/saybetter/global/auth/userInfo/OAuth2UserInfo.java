package com.saybetter.global.auth.userInfo;

public interface OAuth2UserInfo {
	String getProviderId();

	String getProvider();

	String getEmail();

	String getName();
}
