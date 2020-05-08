FROM java:8
ADD lee.jar lee.jar
RUN bash -c 'touch /lee.jar'

ENV SPRING-CLOUD-MS-NAME lee
ENV SPRING-CLOUD-HOSTNAME centos7
ENV TX-MANAGER-URL http://centos7:7000/tx/manager/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/lee.jar"]