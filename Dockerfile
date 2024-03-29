FROM openjdk:21-ea-17-slim-buster
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT java -jar /app.jar --POSTGRES_HOST=$POSTGRES_HOST --POSTGRES_PORT=$POSTGRES_PORT --POSTGRES_DB=$POSTGRES_DB --POSTGRES_USER=$POSTGRES_USER --POSTGRES_PASSWORD=$POSTGRES_PASSWORD