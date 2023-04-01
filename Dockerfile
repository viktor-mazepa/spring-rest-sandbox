FROM openjdk:19-jdk-alpine

WORKDIR /app/spring-rest-sandbox

RUN rm -rf /app/spring-rest-sandbox/*

COPY ./target/spring-rest-sandbox-0.0.1-SNAPSHOT.jar /app/spring-rest-sandbox/spring-rest-sandbox.jar

EXPOSE 8080

CMD ["java","-jar","/app/spring-rest-sandbox/spring-rest-sandbox.jar"]