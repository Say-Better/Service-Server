import org.hidetake.gradle.swagger.generator.GenerateSwaggerUI

plugins {
    id("com.epages.restdocs-api-spec") version "0.19.0"
    id("org.hidetake.swagger.generator") version "2.18.2"
}

dependencies {
    // submodules
    implementation(project(":core:core-common"))
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
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // json
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")

    compileOnly("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("com.jayway.jsonpath:json-path:2.9.0")
    testImplementation("com.google.code.gson:gson:2.10.1")

    // document
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdocVersion")}")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.2")

    // test
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.assertj:assertj-core:3.25.1")
}

openapi3 {
    setServer("http://localhost:8080")
    title = "My API"
    description = "My API description "
    version = "0.1.0"
    format = "yaml"
}

tasks.withType(GenerateSwaggerUI::class.java) {
    dependsOn("openapi3")
}

tasks.register("copySwaggerUI", Copy::class.java) {
    description = "Copy Swagger UI to static resources"
    group = "documentation"
    dependsOn("generateSwaggerUI")

    from("build/resources/main/static/docs")
    into("src/main/resources/static/docs")
}

tasks.getByName("bootJar") {
    enabled = true
    dependsOn("copySwaggerUI")
}

tasks.getByName("jar") {
    enabled = false
}
