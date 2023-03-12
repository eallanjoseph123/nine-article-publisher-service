FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine

WORKDIR /app

COPY target/nine-article-publisher-service-1.0-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "nine-article-publisher-service-1.0-SNAPSHOT.jar"]