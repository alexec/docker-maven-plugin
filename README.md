[![Build Status](https://travis-ci.org/alexec/docker-maven-plugin.svg?branch=master)](https://travis-ci.org/alexec/docker-maven-plugin)

Docker Maven Plugin
===
Project Goal:

* Make it easy to build an app on a container, test it and push it to a Docker repository, even if it relies on other containers (e.g. a database)
* Talk "Maven" rather than "Docker" (E.g. "package" rather than "build").
* Keep it simple.

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
    - target/example-1.0-SNAPSHOT.jar
    - hello-world.yml
# optional list of port to expose on the host
ports:
  - 8080
# containers that this should be linked to, started before this one and stopped afterwards
links:
  - mysql
 ```


Trivial Example
---
Create a default maven project for e.g.

      mvn archetype:generate -DgroupId=com.example -DartifactId=helloworld -DpackageName=com.example -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -Dversion=1.0-SNAPSHOT

Add the following to the `pom.xml`

 ```pom
    <pluginRepositories>
        <pluginRepository>
            <id>sonatype-nexus-snapshots</id>
            <name>Sonatype Nexus Snapshots</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
     ...
    <build>
        ...
        <plugins>
            ...
            <plugin>
                <groupId>com.alexecollins.docker</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </plugin>
        </plugins>
    </build>
 ```

Create your `${basedir}/src/main/docker` directory and create a subfolder for your application container

     mkdir -p src/main/docker/app

If you want to use generated container configuration files (say using property expansion via the [maven-resources-plugin](http://maven.apache.org/plugins/maven-resources-plugin/)) override the `src` configuration property with the location of your generated configuration folders.

 ```pom
            <plugin>
                <groupId>com.alexecollins.docker</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0-SNAPSHOT</version>
                <configuration>
                    <src>target/docker-images</src>
                </configuration>
            </plugin>
 ```
 
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

TODO
---
* Filter resources to add properties (e.g ${project.version}).
* Wait for the service on the container to start.
* Add support for pushing tested containers.

Tear down Docker:

	docker ps -a -q | xargs docker rm
	docker images -a -q | xargs docker rmi
	
Port forward:

	VBoxManage controlvm boot2docker-vm natpf1 "8080,tcp,127.0.0.1,8080,,8080"

References
---
* http://www.alexecollins.com/tags/docker/
