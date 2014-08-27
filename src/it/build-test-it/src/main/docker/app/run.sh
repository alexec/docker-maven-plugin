#!/bin/sh
set -eux

env

sed -i "s/localhost:3306/$EXAMPLE_PROJECT_MYSQL_PORT_3306_TCP_ADDR:3306/" hello-world.yml

java -jar ${project.build.finalName}.jar db migrate hello-world.yml
java -jar ${project.build.finalName}.jar server hello-world.yml
