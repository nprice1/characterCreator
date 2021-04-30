FROM maven:3-adoptopenjdk-11

COPY pom.xml pom.xml
COPY src src/

RUN mvn clean package

EXPOSE 8080
ENTRYPOINT mvn package spring-boot:run
