FROM openjdk:8-jre-alpine

WORKDIR /app

COPY target/nine-article-publisher-service-1.0-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "nine-article-publisher-service-1.0-SNAPSHOT.jar"]