import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("org.asciidoctor.jvm.convert")
    id("org.jlleitschuh.gradle.ktlint")
}

java.sourceCompatibility = JavaVersion.valueOf("VERSION_${property("javaVersion")}")

allprojects {
    group = "${property("projectGroup")}"
    version = "${property("applicationVersion")}"

    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        verbose.set(true)
        outputToConsole.set(true)
        coloredOutput.set(true)

        reporters {
            reporter(ReporterType.CHECKSTYLE)
            reporter(ReporterType.JSON)
            reporter(ReporterType.HTML)
        }

        filter {
            exclude("**/build.gradle.kts", "**/settings.gradle.kts")
        }
    }

    // ktlint report directory location setting
    tasks.withType<GenerateReportsTask> {
        reportsOutputDirectory.set(
            rootProject.layout.buildDirectory.dir("reports/ktlint/${project.name}"),
        )
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.asciidoctor.jvm.convert")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    dependencyManagement {
        imports {
            mavenBom(
                "org.springframework.cloud:spring-cloud-dependencies:${property("springCloudDependenciesVersion")}",
            )
        }
    }

    val asciidoctorExt: Configuration by configurations.creating
    val snippetsDir by extra { file("build/generated-snippets") }

    dependencies {
        // application
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        // ktlint
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // test
        asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
        testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")

        // kapt
        kapt("org.springframework.boot:spring-boot-configuration-processor")
    }

    java.sourceCompatibility = JavaVersion.valueOf("VERSION_${property("javaVersion")}")

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "${project.property("javaVersion")}"
            }
        }

        test {
            useJUnitPlatform {
                excludeTags("develop", "restdocs")
            }
        }

        register<Test>("unitTest") {
            description = "Run unit tests"
            group = "verification"
            useJUnitPlatform {
                excludeTags("develop", "context", "restdocs")
            }
        }

        register<Test>("contextTest") {
            description = "Run tests that require a context"
            group = "verification"
            useJUnitPlatform {
                includeTags("context")
            }
        }

        register<Test>("developTest") {
            description = "Run tests that are in development"
            group = "verification"
            useJUnitPlatform {
                includeTags("develop")
            }
        }

        register<Test>("restDocsTest") {
            description = "Run tests that generate REST documentation"
            group = "verification"
            useJUnitPlatform {
                includeTags("restdocs")
            }
            outputs.dir(snippetsDir)
        }

        asciidoctor {
            inputs.dir(snippetsDir)
            configurations("asciidoctorExt")
            dependsOn("restDocsTest")
            sources {
                include("**/index.adoc")
            }
            baseDirFollowsSourceDir()
        }

        register<Copy>("copyDocs") {
            description = "Copy generated documentation to static resources"
            group = "documentation"
            dependsOn("asciidoctor")

            from("build/docs/asciidoc") {
                include("**/index.html")
            }
            into("src/main/resources/static/docs")
        }

        getByName("bootJar") {
            enabled = false
        }

        getByName("jar") {
            enabled = true
        }
    }
}
