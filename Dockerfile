# Etapa 1: Build do projeto com Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas o necessário
COPY pom.xml ./ 
COPY src ./src

# Build usando Maven já instalado na imagem
RUN mvn clean package -DskipTests -Dmaven.repo.local=/app/.m2

# Etapa 2: Imagem final apenas com JDK e JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
