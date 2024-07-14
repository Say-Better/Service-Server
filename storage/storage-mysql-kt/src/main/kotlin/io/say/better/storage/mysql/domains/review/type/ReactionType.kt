package io.say.better.storage.mysql.domains.review.type

enum class ReactionType(
    description: String,
) {
    TRUE("정반응"),
    FALSE("오반응"),
    NONE("없음"),
}
