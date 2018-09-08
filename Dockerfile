FROM openjdk:8-jre-alpine

MAINTAINER Orlei Bicheski

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

ADD src/main/resources/numbers.txt /usr/share/nc/numbers.txt

# Add the service itself
ADD target/count-*-exec.jar /usr/share/nc/numbers-count.jar

ENTRYPOINT exec java $JAVA_OPTS -jar  "/usr/share/nc/numbers-count.jar"