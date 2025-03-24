FROM openjdk:17-jdk-alpine

LABEL version="1.0" \
      description="Imagem Docker para aplicação Bank pertencente ao Desafio Técnico Nível 1 - Fadesp" \
      authors="Allan Sergio" \
      maintainer="allansergio.dev@gmail.com"

WORKDIR /app

COPY target/bank-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]