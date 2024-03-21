package io.say.better.global.auth;

import java.util.Map;

import io.say.better.domain.member.application.converter.MemberConverter;
import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.global.auth.exception.AuthException;
import io.say.better.global.auth.info.GoogleOAuth2UserInfo;
import io.say.better.global.auth.info.OAuth2UserInfo;
import io.say.better.global.common.code.status.ErrorStatus;
import io.say.better.core.enums.Provider;
import io.say.better.core.enums.RoleType;
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
	 * OAuth2User의 attribute를 담은 Map
	 *
	 * @param provider              OAuth2 로그인 진행 시 키가 되는 필드값
	 * @param userNameAttributeName OAuth2 로그인 진행 시 키가 되는 필드값
	 * @param attributes            OAuth2User의 attribute를 담은 Map
	 * @return OAuthAttributes
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
	 * OAuth2User의 attribute를 담은 Map을 Member로 변환
	 *
	 * @param provider       소셜 로그인 제공자 (구글)
	 * @param oauth2UserInfo 소셜 로그인 유저 정보
	 * @return Member
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
