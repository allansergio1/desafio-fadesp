# Maven
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Spring Boot
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/bank-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Author: Allan Sergio V. Ferreira
LABEL version="1.0" \
description="Imagem Docker para aplicação Bank pertencente ao Desafio Técnico Nível 1 - Fadesp" \
authors="Allan Sergio" \
maintainer="allansergio.dev@gmail.com"