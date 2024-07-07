package io.say.better.domain.member

import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Member

const val EDUCATOR_NAME: String = "educator_name"
const val EDUCATOR_BIRTH_DAY: String = "1998-01-01"
const val EDUCATOR_PROFILE_URL: String = "educator_profile_url"

fun createEducator(
    member: Member,
    name: String = EDUCATOR_NAME,
    birthDay: String = EDUCATOR_BIRTH_DAY,
    profileUrl: String = EDUCATOR_PROFILE_URL,
    id: Long = 0L,
): Educator =
    Educator(
        educatorId = id,
        memberId = member,
        name = name,
        birthDate = birthDay,
        imgUrl = profileUrl,
    )
