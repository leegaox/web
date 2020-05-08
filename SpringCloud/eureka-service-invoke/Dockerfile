FROM carsharing/alpine-oraclejdk8-bash
MAINTAINER deepblueai/lee
ADD eureka-service-invoker.jar eureka-service-invoker.jar
RUN bash -c 'touch eureka-service-invoker.jar'

ENV JAVA_OPTS=""
CMD exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /eureka-service-invoker.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/eureka-service-invoker.jar"]
EXPOSE 8082