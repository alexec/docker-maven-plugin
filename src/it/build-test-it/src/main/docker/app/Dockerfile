FROM centos:centos6

RUN yum -y install java-1.7.0-openjdk-devel.x86_64

ADD example-${project.version}.jar /
ADD hello-world.yml /
ADD run.sh /

RUN chmod +x run.sh

CMD ["./run.sh"]

EXPOSE 8080
EXPOSE 8081
