package io.say.better.storage.mysql.common.converter

import io.say.better.storage.mysql.common.utils.CodedEnum
import io.say.better.storage.mysql.common.utils.CodedEnumUtil
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.util.StringUtils

@Converter
open class AbstractEnumAttributeConverter<E>(
    private val targetEnumClass: Class<E>,
    private val nullable: Boolean,
    private val enumName: String = "code",
) : AttributeConverter<E, String> where E : Enum<E>, E : CodedEnum {
    override fun convertToDatabaseColumn(attribute: E): String {
        if (!nullable && attribute == null) {
            throw IllegalArgumentException("${enumName}을(를) null로 변환할 수 없습니다.")
        }
        return CodedEnumUtil.toCode(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): E {
        if (!nullable && !StringUtils.hasText(dbData)) {
            throw IllegalArgumentException("$enumName(이)가 DB에 null 혹은 Empty로 저장되어 있습니다. DB data = $dbData")
        }
        return CodedEnumUtil.toEnum(targetEnumClass, dbData!!)
    }
}
