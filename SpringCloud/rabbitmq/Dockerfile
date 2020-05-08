#FROM harbor.deepblueai.com/library/openjdk:8-jdk-alpine
FROM carsharing/alpine-oraclejdk8-bash
MAINTAINER lee/rabbitmq
ADD target/rabbitmq.jar rabbitmq.jar
RUN echo "Asia/Shanghai" >/etc/timezone
RUN sh -c 'touch /rabbitmq.jar'
ENV JAVA_OPTS=""
CMD exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /rabbitmq.jar
EXPOSE 8201