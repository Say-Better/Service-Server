package io.say.better.storage.mysql.domain.constant

enum class ProgressState(description: String) {
    READY("수업 가능"),
    PROCESSING("수업 진행"),
    HAVE_TO_REVIEW("수업 리뷰"),
    TERMINATED("수업 종료");
}
