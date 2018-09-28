FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD im-netty-1.0-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 /app.jar