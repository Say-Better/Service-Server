package io.say.better.global.auth

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.core.enums.auth.info.GoogleOAuth2UserInfo
import io.say.better.core.enums.auth.info.OAuth2UserInfo
import io.say.better.domain.member.application.converter.MemberConverter
import io.say.better.global.auth.exception.AuthException
import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.storage.mysql.domain.entity.Member

data class OAuthAttributes(
    // OAuth2 로그인 시 키가 되는 필드값
    val key: String,
    // OAuth2 로그인 유저 정보
    val userInfo: OAuth2UserInfo,
) {
    /**
     * OAuth2User의 attribute를 담은 Map을 Member로 변환
     *
     * @param provider       소셜 로그인 제공자 (구글)
     * @param oauth2UserInfo 소셜 로그인 유저 정보
     * @return Member
     */
    fun toEntity(
        provider: Provider,
        oauth2UserInfo: OAuth2UserInfo,
    ): Member {
        val loginId = oauth2UserInfo.provider + "_" + oauth2UserInfo.providerId

        return MemberConverter.toMember(
            email = oauth2UserInfo.email,
            birthDate = "",
            role = RoleType.NONE,
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
