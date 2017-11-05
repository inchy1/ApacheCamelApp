FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/camel-1.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,suspend=n,server=y"
EXPOSE 8080 8000
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar