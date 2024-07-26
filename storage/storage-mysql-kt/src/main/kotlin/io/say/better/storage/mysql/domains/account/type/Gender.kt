package io.say.better.storage.mysql.domains.account.type

import io.say.better.storage.mysql.common.utils.CodedEnum

enum class Gender(
    val code: String,
    val description: String,
) : CodedEnum {
    MALE("1", "남성"),
    FEMALE("2", "여성"),
    ETC("0", "기타"),
    ;

    override fun code(): String = code
}
