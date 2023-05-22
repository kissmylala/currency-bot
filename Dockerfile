
FROM openjdk:17-alpine

RUN mkdir -p /app/

RUN mkdir -p /var/log/com.telegram/currency_bot/

COPY target/currency_bot-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]