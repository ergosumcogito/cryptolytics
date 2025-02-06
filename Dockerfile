# Stage 1: Build Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/

RUN mvn clean install -Ddocker.skip=true

# Stage 2: Java runtime environment
FROM eclipse-temurin:21-jammy

WORKDIR /app

COPY --from=build /app/target/cryptolytics-*.jar /app/cryptolytics.jar

EXPOSE 8080

CMD ["java", "-jar", "cryptolytics.jar"]