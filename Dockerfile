FROM gradle:7.6-jdk AS builder

COPY core/core-api/src/main ./core/core-api/src/main
COPY core/core-api/build.gradle ./core/core-api/build.gradle
COPY core/core-api/lombok.config ./core/core-api/lombok.config

COPY core/core-enum/src/main ./core/core-enum/src/main
COPY core/core-enum/build.gradle ./core/core-enum/build.gradle

COPY storage/storage-mysql/src/main ./storage/storage-mysql/src/main
COPY storage/storage-mysql/build.gradle ./storage/storage-mysql/build.gradle

COPY storage/storage-redis/src/main ./storage/storage-redis/src/main
COPY storage/storage-redis/build.gradle ./storage/storage-redis/build.gradle

COPY storage/storage-rabbitmq/src/main ./storage/storage-rabbitmq/src/main
COPY storage/storage-rabbitmq/build.gradle ./storage/storage-rabbitmq/build.gradle

COPY clients/client-api/src/main ./clients/client-api/src/main
COPY clients/client-api/build.gradle ./clients/client-api/build.gradle

COPY support/logging/src/main ./support/logging/src/main
COPY support/logging/build.gradle ./support/logging/build.gradle

COPY support/monitoring/src/main ./support/monitoring/src/main
COPY support/monitoring/build.gradle ./support/monitoring/build.gradle

COPY tests/api-docs/src/test ./tests/api-docs/src/test
COPY tests/api-docs/build.gradle ./tests/api-docs/build.gradle

COPY build.gradle settings.gradle gradle.properties ./

RUN gradle :core:core-api:buildNeeded --stacktrace --info --refresh-dependencies -x test --warning-mode=all

RUN cp core/core-api/build/libs/*.jar /app.jar
# Run stage
FROM openjdk:17.0.1-jdk-slim AS run

COPY --from=builder /app.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","-Duser.timezone=Asia/Seoul", "app.jar"]
