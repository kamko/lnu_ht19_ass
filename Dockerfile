FROM openjdk:13.0.1-slim-buster

COPY build/libs/lnu_ass-*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]