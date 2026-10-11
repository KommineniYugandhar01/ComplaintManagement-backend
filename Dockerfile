
# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy Java source code
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the generated JAR
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE ${PORT:8080}

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]