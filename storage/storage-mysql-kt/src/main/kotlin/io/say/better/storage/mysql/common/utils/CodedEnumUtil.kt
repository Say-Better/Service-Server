package io.say.better.storage.mysql.common.utils

fun interface CodedEnum {
    fun code(): String
}

class CodedEnumUtil {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun <T> toEnum(
            enumClass: Class<T>,
            code: String,
        ): T where T : Enum<T>, T : CodedEnum = enumClass.enumConstants.first { it.code() == code }

        fun <T> toCode(enumClass: T): String where T : Enum<T>, T : CodedEnum = enumClass.code()
    }
}
