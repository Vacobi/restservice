FROM gradle:8.3-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle build -x test --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://master_postgres:5433/restservice_db
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=pswd


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
