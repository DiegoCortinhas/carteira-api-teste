FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/carteira-api.jar

EXPOSE 8081

CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=${PORT} -jar carteira-api.jar