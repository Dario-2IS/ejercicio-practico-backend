# Etapa de build
FROM gradle:8.4.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Etapa de runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
