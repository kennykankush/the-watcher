FROM maven:3.9.9-eclipse-temurin-23

LABEL name="the-watcher"

ARG APP_DIR=/app

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT java -jar target/the-watcher-0.0.1-SNAPSHOT.jar
