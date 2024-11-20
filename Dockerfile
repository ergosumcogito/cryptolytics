# Stage 1: Build Maven
FROM maven:3.9.9-eclipse-temurin-21 as build

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/

# Execute tests
RUN mvn clean install -DskipTests

# Stage 2: Java runtime environment
FROM eclipse-temurin:21-jammy

WORKDIR /app

# Uncomment this for working with local db
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/crypto_database
#ENV SPRING_DATASOURCE_USERNAME=db_admin
#ENV SPRING_DATASOURCE_PASSWORD=db_admin

COPY --from=build /app/target/cryptolytics-*.jar /app/cryptolytics.jar

EXPOSE 8080

CMD ["java", "-jar", "cryptolytics.jar"]