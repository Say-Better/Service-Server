package io.say.better.core.enums

enum class RoleType(
    val description: String
) {
    EDUCATOR("교육자"),
    LEARNER("학습자"),
    ADMIN("관리자"),
    NONE("초기 상태");
}
