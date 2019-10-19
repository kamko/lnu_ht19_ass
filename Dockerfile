FROM adoptopenjdk:11.0.4_11-jre-hotspot

COPY build/libs/lnu_ass-*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]