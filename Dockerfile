FROM openjdk:8-jre-alpine
MAINTAINER Orlei

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

# Temporary folder > Logger
# RUN mkdir /tmp/
VOLUME /tmp/

# Add the service itself
ADD target/count-*-exec.jar /usr/share/nc/numbers-count.jar
ENTRYPOINT exec java $JAVA_OPTS -jar  "/usr/share/nc/numbers-count.jar"
