#!/bin/sh
set -eux

env

# by default we use the long name of the project, but we can use an alias instead
# sed -i "s/localhost:3306/$EXAMPLE_PROJECT_MYSQL_PORT_3306_TCP_ADDR:${mysql.port}/" hello-world.yml
sed -i "s/localhost:3306/$DB_PORT_3306_TCP_ADDR:${mysql.port}/" hello-world.yml

java -jar ${project.build.finalName}.jar db migrate hello-world.yml
java -jar ${project.build.finalName}.jar server hello-world.yml
