FROM dockerfile/java:oracle-java7

WORKDIR /

ADD ${project.build.finalName}.jar /
ADD hello-world.yml /
ADD run.sh /

RUN chmod +x run.sh

CMD ["./run.sh"]

EXPOSE 8080
EXPOSE 8081
