# Stage 1: Build aplikasi menggunakan Maven
# Gunakan image Maven + Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY demo/ /app
RUN ./mvnw clean package -DskipTests

# Stage 2: Jalankan jar dengan OpenJDK
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]