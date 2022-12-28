FROM openjdk:11-jre-slim

RUN apt-get update; apt-get install -y fontconfig libfreetype6
RUN mkdir "/log"

ENV TZ="Asia/Jakarta"

COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar","-Djava.net.preferIPv4Stack=true"]