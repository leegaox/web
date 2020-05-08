FROM carsharing/alpine-oraclejdk8-bash
MAINTAINER deepblueai/lee
ADD eureka-service-provider.jar eureka-service-provider.jar
RUN bash -c 'touch eureka-server-provider.jar'

ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/eureka-service-provider.jar"]
EXPOSE 8081