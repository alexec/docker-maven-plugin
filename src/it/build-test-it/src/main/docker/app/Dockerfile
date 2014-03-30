FROM centos

RUN yum -y install java-1.7.0-openjdk-devel.x86_64 

ADD example-1.0-SNAPSHOT.jar /
ADD hello-world.yml /

CMD ["java", "-jar", "example-*.jar", "server", "hello-world.yml"]

EXPOSE 8080
EXPOSE 8081
