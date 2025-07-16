# # Stage 1: Build aplikasi menggunakan Maven
# # Gunakan image Maven + Java 21
# FROM maven:3.9.6-eclipse-temurin-21 AS build
# WORKDIR /app
# COPY . .
# RUN ./mvnw clean package -DskipTests

# # Stage 2: Jalankan jar dengan OpenJDK
# FROM eclipse-temurin:21-jdk
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# ENTRYPOINT ["java", "-jar", "/app/app.jar"]


# Stage 1: Build aplikasi menggunakan Maven
# Gunakan image Maven + Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Jalankan jar dengan OpenJDK
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# --- ADD THESE LINES FOR DEBUGGING ---
# eclipse-temurin:21-jdk is based on Debian, so use apt-get
RUN apt-get update && apt-get install -y netcat-traditional && rm -rf /var/lib/apt/lists/*
# The 'rm -rf /var/lib/apt/lists/*' cleans up apt cache to keep image small
# -------------------------------------

ENTRYPOINT ["java", "-jar", "/app/app.jar"]