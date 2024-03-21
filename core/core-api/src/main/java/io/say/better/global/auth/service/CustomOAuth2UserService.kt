package io.say.better.global.auth.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.MemberReadRepository;
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository;
import io.say.better.global.auth.exception.AuthException;
import io.say.better.global.common.code.status.ErrorStatus;
import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.global.auth.CustomOAuth2User;
import io.say.better.global.auth.OAuthAttributes;
import io.say.better.core.enums.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberReadRepository memberReadRepository;
	private final MemberWriteRepository memberWriteRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

		// DefaultOAuth2UserService를 사용하여 OAuth2User 객체를 생성
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		// registrationId : OAuth2 로그인을 처리하는 서비스를 구분하는 코드
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Provider provider = Provider.find(registrationId)
				.orElseThrow(() -> new AuthException(ErrorStatus.INTERNAL_SERVER_ERROR));
		String userNameAttributeName = userRequest.getClientRegistration()
				.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
		Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

		// socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
		OAuthAttributes extractAttributes = OAuthAttributes.of(provider, userNameAttributeName, attributes);

		Member createdUser = getUser(extractAttributes, provider); // getUser() 메소드로 User 객체 생성 후 반환

		// DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
		return new CustomOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getDescription())),
				attributes,
				extractAttributes.getKey(),
				createdUser.getEmail(),
				createdUser.getRole()
		);
	}

	/**
	 * OAuthAttributes 객체를 통해 User 객체 생성
	 * DB에 해당 User가 존재하지 않으면 생성 후 반환
	 *
	 * @param attributes OAuthAttributes 객체
	 * @param provider   Provider 객체
	 * @return Member
	 */
	private Member getUser(OAuthAttributes attributes, Provider provider) {
		String loginId = attributes.getUserInfo().getProvider() + "_" + attributes.getUserInfo().getProviderId();
		Member findUser = memberReadRepository.findByProviderAndLoginId(provider, loginId).orElse(null);

		if (findUser == null) {
			return saveUser(attributes, provider);
		}
		return findUser;
	}

	/**
	 * OAuthAttributes 객체를 통해 User 객체 생성 후 DB에 저장
	 *
	 * @param attributes OAuthAttributes 객체
	 * @param provider   Provider 객체
	 * @return Member
	 */
	private Member saveUser(OAuthAttributes attributes, Provider provider) {
		Member createdUser = attributes.toEntity(provider, attributes.getUserInfo());
		return memberWriteRepository.save(createdUser);
	}
}
