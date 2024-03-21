package io.say.better.global.auth.service

import io.say.better.core.enums.Provider
import io.say.better.global.auth.CustomOAuth2User
import io.say.better.global.auth.OAuthAttributes
import io.say.better.global.auth.exception.AuthException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.config.logger.logger
import io.say.better.storage.mysql.dao.repository.MemberReadRepository
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository
import io.say.better.storage.mysql.domain.entity.Member
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class CustomOAuth2UserService(
    private val memberReadRepository: MemberReadRepository,
    private val memberWriteRepository: MemberWriteRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val log = logger()

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입")

        // DefaultOAuth2UserService를 사용하여 OAuth2User 객체를 생성
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        // registrationId : OAuth2 로그인을 처리하는 서비스를 구분하는 코드
        val registrationId = userRequest.clientRegistration.registrationId
        val provider = Provider.find(registrationId)
            .orElseThrow { AuthException(ErrorStatus.INTERNAL_SERVER_ERROR) }
        val userNameAttributeName = userRequest.clientRegistration
            .providerDetails.userInfoEndpoint.userNameAttributeName // OAuth2 로그인 시 키(PK)가 되는 값
        val attributes = oAuth2User.attributes // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        val extractAttributes: OAuthAttributes =
            OAuthAttributes.Companion.of(provider, userNameAttributeName, attributes)

        val createdUser = getUser(extractAttributes, provider) // getUser() 메소드로 User 객체 생성 후 반환

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return CustomOAuth2User(
            setOf(SimpleGrantedAuthority(createdUser.role.description)),
            attributes,
            extractAttributes.key,
            createdUser.email,
            createdUser.role
        )
    }

    /**
     * OAuthAttributes 객체를 통해 User 객체 생성
     * DB에 해당 User가 존재하지 않으면 생성 후 반환
     *
     * @param attributes OAuthAttributes 객체
     * @param provider   Provider 객체
     * @return Member
     */
    private fun getUser(attributes: OAuthAttributes, provider: Provider): Member {
        val loginId = attributes.userInfo.provider + "_" + attributes.userInfo.providerId
        val findUser = memberReadRepository!!.findByProviderAndLoginId(provider, loginId).orElse(null)
            ?: return saveUser(attributes, provider)

        return findUser
    }

    /**
     * OAuthAttributes 객체를 통해 User 객체 생성 후 DB에 저장
     *
     * @param attributes OAuthAttributes 객체
     * @param provider   Provider 객체
     * @return Member
     */
    private fun saveUser(attributes: OAuthAttributes, provider: Provider): Member {
        val createdUser = attributes.toEntity(provider, attributes.userInfo)
        return memberWriteRepository!!.save(createdUser)
    }
}
