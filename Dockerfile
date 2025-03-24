# Author: Allan Sergio V. Ferreira
LABEL version="1.0" \
description="Imagem Docker para aplicação Bank pertencente ao Desafio Técnico Nível 1 - Fadesp" \
authors="Allan Sergio" \
maintainer="allansergio.dev@gmail.com"

# Maven
FROM maven:3.9.9-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Spring Boot
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/bank-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]