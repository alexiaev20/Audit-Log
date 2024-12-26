# Etapa 1: Construção da aplicação com Maven
FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/audit-log-crud-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
