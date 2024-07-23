rootProject.name = "say-better-spring-boot"

include(
    ":core:core-api",
    ":core:core-common",
    ":core:core-infra",
    ":storage:storage-mysql-kt",
    ":storage:storage-rabbitmq-kt",
    ":storage:storage-redis-kt",
    ":clients:client-api",
    ":support:logging",
    ":support:monitoring",
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val asciidoctorConvertVersion: String by settings
    val ktlintVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.asciidoctor.jvm.convert" -> useVersion(asciidoctorConvertVersion)
                "org.jlleitschuh.gradle.ktlint" -> useVersion(ktlintVersion)
                "org.gradle.toolchains.foojay-resolver-convention" -> useVersion("0.5.0")
            }
        }
    }
}
