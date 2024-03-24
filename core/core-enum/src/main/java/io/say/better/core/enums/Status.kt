package io.say.better.core.enums

enum class Status(
    val description: String
) {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    DELETED("삭제 대기");
}
