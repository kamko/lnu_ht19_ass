FROM adoptopenjdk/openjdk13:jre-13_33-ubi-minimal

COPY build/libs/lnu_ass-*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]