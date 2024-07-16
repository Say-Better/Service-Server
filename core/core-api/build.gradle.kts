plugins {
    id("com.epages.restdocs-api-spec") version "0.18.2"
}

dependencies {
    // submodules
    implementation(project(":core:core-common"))
    implementation(project(":core:core-infra"))
    implementation(project(":support:monitoring"))
    implementation(project(":support:logging"))
    implementation(project(":storage:storage-mysql-kt"))
    implementation(project(":storage:storage-redis-kt"))
    implementation(project(":storage:storage-rabbitmq-kt"))
    implementation(project(":clients:client-api"))

    testImplementation(project(":tests:api-docs"))

    // application
    implementation("org.springframework.boot:spring-boot-starter-web")

    // security && OAuth
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // utils
    implementation("org.apache.commons:commons-lang3")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // json
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")

    compileOnly("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("com.jayway.jsonpath:json-path:2.9.0")
    testImplementation("com.google.code.gson:gson:2.10.1")

    // test
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.assertj:assertj-core:3.25.1")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
