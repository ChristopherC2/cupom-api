FROM eclipse-temurin:17-jre-jammy

ENV TZ=America/Sao_Paulo

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
