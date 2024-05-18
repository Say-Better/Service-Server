package io.say.better.global.auth.service

import io.say.better.core.enums.Provider
import io.say.better.global.auth.CustomOAuth2User
import io.say.better.global.auth.OAuthAttributes
import io.say.better.global.auth.exception.AuthException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.config.logger.logger
import io.say.better.storage.mysql.dao.repository.EducatorReadRepository
import io.say.better.storage.mysql.dao.repository.EducatorWriteRepository
import io.say.better.storage.mysql.dao.repository.LearnerReadRepository
import io.say.better.storage.mysql.dao.repository.LearnerWriteRepository
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
        private val educatorReadRepository: EducatorReadRepository,
        private val educatorWriteRepository: EducatorWriteRepository,
        private val learnerReadRepository: LearnerReadRepository,
        private val learnerWriteRepository: LearnerWriteRepository
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
                OAuthAttributes.of(provider, userNameAttributeName, attributes)

        val createdUser = getEducator(extractAttributes, provider) // getUser() 메소드로 User 객체 생성 후 반환

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return CustomOAuth2User(
                setOf(SimpleGrantedAuthority(createdUser.role?.description)),
                attributes,
                extractAttributes.key,
                createdUser.email!!
        )
    }

    /**
     * OAuthAttributes 객체를 통해 User 객체 생성
     * DB에 해당 User가 존재하지 않으면 생성 후 반환
     *
     * @param attributes OAuthAttributes 객체
     * @param provider   Provider 객체
     * @return Educator
     */
    private fun getEducator(attributes: OAuthAttributes, provider: Provider): Educator {
        val loginId = attributes.userInfo.provider + "_" + attributes.userInfo.providerId
        val findUser = educatorReadRepository.findByProviderAndLoginId(provider, loginId)
                .orElse(null) ?: return saveEducator(attributes, provider)

        return findUser
    }

    private fun getLearner(attributes: OAuthAttributes, provider: Provider): Learner {
        val loginId = attributes.userInfo.provider + "_" + attributes.userInfo.providerId
        val findUser = learnerReadRepository.findByProviderAndLoginId(provider, loginId)
                .orElse(null) ?: return saveLearner(attributes, provider)

        return findUser
    }

    /**
     * OAuthAttributes 객체를 통해 Educator 객체 생성 후 DB에 저장
     *
     * @param attributes OAuthAttributes 객체
     * @param provider   Provider 객체
     * @return Educator
     */
    private fun saveEducator(attributes: OAuthAttributes, provider: Provider): Educator {
        val createdUser = attributes.toEducatorEntity(provider, attributes.userInfo)
        return educatorWriteRepository.save(createdUser)
    }

    /**
     * OAuthAttributes 객체를 통해 Learner 객체 생성 후 DB에 저장
     *
     * @param attributes OAuthAttributes 객체
     * @param provider   Provider 객체
     * @return Learner
     */
    private fun saveLearner(attributes: OAuthAttributes, provider: Provider): Learner {
        val createdUser = attributes.toLearnerEntity(provider, attributes.userInfo)
        return learnerWriteRepository.save(createdUser)
    }
}
