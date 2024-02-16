FROM openjdk:17
LABEL authors="chltj"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","-Duser.timezone=Asia/Seoul", "/app.jar"]
