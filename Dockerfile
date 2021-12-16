FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/carteira-api.jar

EXPOSE 8081

CMD java -XX:+UseContainerSupport -jar carteira-api.jar