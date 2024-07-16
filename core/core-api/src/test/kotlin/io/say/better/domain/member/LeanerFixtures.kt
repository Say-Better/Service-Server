package io.say.better.domain.member

import io.say.better.storage.mysql.domains.account.entity.Learner
import io.say.better.storage.mysql.domains.account.entity.Member
import io.say.better.storage.mysql.domains.account.type.Gender

const val LEARNER_NAME: String = "leaner_name"
const val LEARNER_BIRTH_DAY: String = "2010-01-01"
const val LEARNER_PROFILE_URL: String = "leaner_profile_url"

val GENDER: Gender = Gender.MALE

fun createLearner(
    member: Member,
    name: String = LEARNER_NAME,
    birthDay: String = LEARNER_BIRTH_DAY,
    gender: Gender = GENDER,
    profileUrl: String = LEARNER_PROFILE_URL,
    id: Long = 0L,
): Learner =
    Learner(
        learnerId = id,
        memberId = member,
        name = name,
        birthDate = birthDay,
        gender = gender,
        imgUrl = profileUrl,
    )
