#FROM carsharing/alpine-oraclejdk8-bash
FROM java:openjdk-8-jdk-alpine
MAINTAINER deepblueai/lee
ADD eureka-server-center.jar eureka-server-center.jar
RUN set -xe && apk --no-cache add ttf-dejavu fontconfig
RUN echo "Asia/Shanghai" >/etc/timezone
RUN sh -c 'touch eureka-server-center.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/eureka-server-center.jar"]
EXPOSE 8761