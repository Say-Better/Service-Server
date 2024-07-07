package io.say.better.domain.member

import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.storage.mysql.domain.entity.Member

const val MEMBER_NAME: String = "name"
const val EMAIL: String = "test@gmail.com"
const val SOCIAL_PROVIDER_ID: String = "providerId"
const val COMMON_PROVIDER_ID: String = "commonProviderId"
const val MEMBER_BIRTH_DAY: String = "1990-01-01"

val EDUCATOR_ROLE: RoleType = RoleType.EDUCATOR_LEARNER
val LEARNER_ROLE: RoleType = RoleType.LEARNER
val EDUCATOR_LEARNER_ROLE: RoleType = RoleType.EDUCATOR_LEARNER

fun createCommonMember(
    name: String = MEMBER_NAME,
    email: String = EMAIL,
    providerId: String = COMMON_PROVIDER_ID,
    birthDay: String = MEMBER_BIRTH_DAY,
    role: RoleType = EDUCATOR_ROLE,
): Member =
    Member.createMember(
        name = name,
        email = email,
        provider = Provider.COMMON,
        providerId = providerId,
        loginId = "${Provider.COMMON}-$providerId",
        birthDate = birthDay,
        role = role,
    )

fun createSocialMember(
    name: String = MEMBER_NAME,
    email: String = EMAIL,
    providerId: String = SOCIAL_PROVIDER_ID,
    birthDay: String = MEMBER_BIRTH_DAY,
    role: RoleType = EDUCATOR_ROLE,
    provider: Provider = Provider.GOOGLE,
): Member =
    Member.createMember(
        name = name,
        email = email,
        provider = provider,
        providerId = providerId,
        loginId = "${provider.name}-$providerId",
        birthDate = birthDay,
        role = role,
    )

fun createCommonLoginRequest(
    name: String = MEMBER_NAME,
    email: String = EMAIL,
    birthDay: String = MEMBER_BIRTH_DAY,
): AuthRequest.CommonLoginDTO = AuthRequest.CommonLoginDTO(name, email, birthDay)
