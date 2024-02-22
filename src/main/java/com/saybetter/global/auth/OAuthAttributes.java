package com.saybetter.global.auth;

import java.util.Map;

import com.saybetter.domain.member.application.converter.MemberConverter;
import com.saybetter.domain.member.domain.Member;
import com.saybetter.global.auth.exception.AuthException;
import com.saybetter.global.auth.userInfo.GoogleOAuth2UserInfo;
import com.saybetter.global.auth.userInfo.OAuth2UserInfo;
import com.saybetter.global.common.code.status.ErrorStatus;
import com.saybetter.global.common.constant.Provider;
import com.saybetter.global.common.constant.RoleType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private String key; // OAuth2 로그인 시 키가 되는 필드값
	private OAuth2UserInfo userInfo; // OAuth2 로그인 유저 정보

	@Builder
	public OAuthAttributes(String key, OAuth2UserInfo userInfo) {
		this.key = key;
		this.userInfo = userInfo;
	}

	/**
	 * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
	 * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
	 * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
	 * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
	 */
	public static OAuthAttributes of(
			Provider provider,
			String userNameAttributeName,
			Map<String, Object> attributes
	) {
		if (provider.equals(Provider.GOOGLE)) {
			return ofGoogle(userNameAttributeName, attributes);
		} else {
			throw new AuthException(ErrorStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.key(userNameAttributeName)
				.userInfo(new GoogleOAuth2UserInfo(attributes))
				.build();
	}

	/**
	 * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
	 * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
	 * email에는 UUID로 중복 없는 랜덤 값 생성
	 * role은 GUEST로 설정
	 */
	public Member toEntity(Provider provider, OAuth2UserInfo oauth2UserInfo) {
		String loginId = oauth2UserInfo.getProvider() + "_" + oauth2UserInfo.getProviderId();
		return MemberConverter.toMember(
				oauth2UserInfo.getEmail(),
				null,
				RoleType.NONE,
				provider,
				oauth2UserInfo.getProviderId(),
				loginId,
				oauth2UserInfo.getName()
		);
	}

}
