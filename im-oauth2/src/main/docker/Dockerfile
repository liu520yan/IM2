FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD im-oauth2-1.0-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar