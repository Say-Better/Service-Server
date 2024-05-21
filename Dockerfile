FROM openjdk:17
ARG JAR_FILE=core/core-api/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=dev","-Duser.timezone=Asia/Seoul"]
