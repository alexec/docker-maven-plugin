#!/bin/sh
set -eux

sed -i "s/localhost:3306/$EXAMPLE_MYSQL_PORT_3306_TCP_ADDR:3306/" hello-world.yml

java -jar example-1.0-SNAPSHOT.jar db migrate hello-world.yml
java -jar example-1.0-SNAPSHOT.jar server hello-world.yml
