#!/bin/sh
set -eux

MYSQL_HOST=$(env|grep PORT_3306_TCP_ADDR|sed 's/.*=//')
sed -i "s/localhost:3306/$MYSQL_HOST:3306/" hello-world.yml

java -jar example-1.0-SNAPSHOT.jar db migrate hello-world.yml
java -jar example-1.0-SNAPSHOT.jar server hello-world.yml
