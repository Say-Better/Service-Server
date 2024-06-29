package io.say.better.core.infra.enums

enum class AwsS3Folder(
    private val folder: String,
) {
    MEMBER("member"),
    ;

    fun getFolder() = folder
}
