[![Build Status](https://travis-ci.org/alexec/docker-maven-plugin.svg?branch=master)](https://travis-ci.org/alexec/docker-maven-plugin)

Docker Maven Plugin
===
Project Goal:

* Make it easy to build an app on a container, test it and push it to a Docker repository, even if it relies on other containers (e.g. a database)
* Talk "Maven" rather than "Docker" (E.g. "package" rather than "build").
* Keep it simple.

Goals
---
* `clean` - delete all containers and images for the project (see know issues)
* `package` - builds the containers based on YAML configuration
* `start` - start the containers in order and ensures they are running
* `stop` - stop all running containers for the project
* `deploy` - push containers to Docker repository

Pre-requisites
---
Docker installed and Docker daemon running, see the docker [getting started guide](https://www.docker.io/gettingstarted/) for e.g. on a mac follow these [instructions](http://docs.docker.io/en/latest/installation/mac/).

Usage
---
The best example to look at is the [one from the tests](src/it/build-test-it) which creates a [Drop-Wizard](https://dropwizard.github.io/dropwizard/) app and builds three containers: __app__ (the dropwizard application) __data__ and __mysql__, and then runs an integration test against the deployed app. Et voila a packaged image!

Typically, you build your app, run your standard unit tests and package it as usual. Then, you build a container with your app deployed onto it, and run integration tests against it. If they pass, deploy your jar into the Maven repository, and optionally, your image into a Docker repository.

To use the plugin, you need to define a docker directory in `${basedir}/src/main` which will include a subdirectory for each container that you wish to deploy.

- `src/main/docker/` contains one folder per container for e.g. the mysql container would have a folder structure as follows:
    - mysql
        - `Dockerfile` a standard Docker file.
        - `conf.yml` configuration:
    - ...
        - `Dockerfile` a standard Docker file.
        - `conf.yml` configuration:

```yml
# additional data require to create the Docker image
packaging:
  # files to add to the build, usually used with ADD in the Dockerfile
  add:
    - target/example-${project.version}.jar
    - hello-world.yml
# optional list of port to expose on the host
ports:
  - 8080
# containers that this should be linked to, started before this one and stopped afterwards
links:
  - mysql
healthChecks:
  pings:
    - url: http://localhost:8080/health-check
      timeout: 60000
# tag to use for images
tag: alex.e.c/app:${project.artifactId}-${project.version}
 ```

Add the following to the `pom.xml` plugins section.

 ```pom.xml
            <plugin>
                <groupId>com.alexecollins.docker</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <configuration>
                    <!-- your installed version -->
                    <version>1.9<version>
                    <!-- used for push -->
                    <username>alexec</username>
                    <email>alex.e.c@gmail.com</email>
                    <!-- remove images created by Dockerfile -->
                    <removeIntermediateImages>true</removeIntermediateImages>
                    <!-- do/do not cache images (default true), disable to get the freshest images -->
                    <cache>true</cache>
                </configuration>
            </plugin>
 ```

Create your `${basedir}/src/main/docker` directory and create a subfolder for your application container

     mkdir -p src/main/docker/app
 
Define your Dockerfile and conf.yml and place in ${basedir}/src/main/docker/app

 ```tree
    src/main/docker/app
    ├── Dockerfile
    └── conf.yml
 ```

You can now invoke functionality from the plugin, information on the plugin can be found by running the following command

     mvn docker:help

For e.g. to build containers from their `Dockerfile` and `conf.yml` files, run the following command

     mvn docker:package

Change Log
---
1.4.0

* (https://github.com/alexec/docker-maven-plugin/issues/15)[Issue 15] - support `cache` parameter

1.3.1

* Issue 5 - removed logging of binary to console 

1.3.0

* Issue 8 - support removal of intermediate containers
* Issue 11 - correct spelling in README.md
* Issue 12 - added skip option

Tips
---
Tear down Docker:

	docker ps -a -q | xargs docker rm -fv
	docker images -a -q | xargs docker rmi -f
	
Port forward:

	VBoxManage controlvm boot2docker-vm natpf1 "8080,tcp,127.0.0.1,8080,,8080"

References
---
* http://www.alexecollins.com/tags/docker/
