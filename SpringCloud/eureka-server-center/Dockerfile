FROM carsharing/alpine-oraclejdk8-bash
MAINTAINER deepblueai/lee
ADD eureka-server-center.jar eureka-server-center.jar
RUN bash -c 'touch eureka-server-center.jar'

ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/eureka-server-center.jar"]
EXPOSE 8761