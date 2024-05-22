package io.say.better.global.auth

import io.say.better.core.enums.Provider
import io.say.better.domain.member.application.converter.EducatorConverter
import io.say.better.domain.member.application.converter.LearnerConverter
import io.say.better.global.auth.exception.AuthException
import io.say.better.global.auth.info.GoogleOAuth2UserInfo
import io.say.better.global.auth.info.OAuth2UserInfo
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner

data class OAuthAttributes(
    // OAuth2 로그인 시 키가 되는 필드값
    val key: String,
    // OAuth2 로그인 유저 정보
    val userInfo: OAuth2UserInfo,
) {
    /**
     * OAuth2User의 attribute를 담은 Map을 Educator로 변환
     *
     * @param provider       소셜 로그인 제공자 (구글)
     * @param oauth2UserInfo 소셜 로그인 유저 정보
     * @return Educator
     */
    fun toEducatorEntity(
        provider: Provider,
        oauth2UserInfo: OAuth2UserInfo,
    ): Educator {
        val loginId = oauth2UserInfo.provider + "_" + oauth2UserInfo.providerId

        return EducatorConverter.toEducator(
            email = oauth2UserInfo.email,
            birthDate = null,
            provider = provider,
            providerId = oauth2UserInfo.providerId,
            loginId = loginId,
            name = oauth2UserInfo.name,
        )
    }

    /**
     * OAuth2User의 attribute를 담은 Map을 Learner로 변환
     *
     * @param provider       소셜 로그인 제공자 (구글)
     * @param oauth2UserInfo 소셜 로그인 유저 정보
     * @return Educator
     */
    fun toLearnerEntity(
        provider: Provider,
        oauth2UserInfo: OAuth2UserInfo,
    ): Learner {
        val loginId = oauth2UserInfo.provider + "_" + oauth2UserInfo.providerId

        return LearnerConverter.toLearner(
            email = oauth2UserInfo.email,
            birthDate = null,
            provider = provider,
            providerId = oauth2UserInfo.providerId,
            loginId = loginId,
            name = oauth2UserInfo.name,
        )
    }

    companion object {
        /**
         * OAuth2User의 attribute를 담은 Map
         *
         * @param provider              OAuth2 로그인 진행 시 키가 되는 필드값
         * @param userNameAttributeName OAuth2 로그인 진행 시 키가 되는 필드값
         * @param attributes            OAuth2User의 attribute를 담은 Map
         * @return OAuthAttributes
         */
        fun of(
            provider: Provider,
            userNameAttributeName: String,
            attributes: Map<String, Any>?,
        ): OAuthAttributes {
            if (provider == Provider.GOOGLE) {
                return ofGoogle(userNameAttributeName, attributes)
            } else {
                throw AuthException(ErrorStatus.INTERNAL_SERVER_ERROR)
            }
        }

        private fun ofGoogle(
            userNameAttributeName: String,
            attributes: Map<String, Any>?,
        ): OAuthAttributes {
            return OAuthAttributes(userNameAttributeName, GoogleOAuth2UserInfo(attributes))
        }
    }
}
