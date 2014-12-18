Usage
===
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

To use the plugin, you need to define a `docker/` directory in `${basedir}/src/main` which will include a subdirectory for each container that you wish to deploy.

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
# containers that this should be linked to, started before this one and stopped afterwards, optional alias after colon
links:
  - mysql:db
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
        <version>1.13</version>
        <!-- used for push -->
        <username>alexec</username>
        <email>alex.e.c@gmail.com</email>
        <!-- change here if you are using another port/host, e.g. 4243 -->
        <host>http://localhost:2375</host>
        <!-- if you need to run over SSL, change this -->
        <dockerCertPath>${user.home}/.docker</dockerCertPath>
        <!-- remove images created by Dockerfile -->
        <removeIntermediateImages>true</removeIntermediateImages>
        <!-- do/do not cache images (default true), disable to get the freshest images -->
        <cache>true</cache>
    </configuration>
    <dependencies>
        <dependency>
            <!-- set-up port forwarding if you're using boot2docker -->
            <groupId>com.alexecollins.docker</groupId>
            <artifactId>docker-java-orchestration-plugin-boot2docker</artifactId>
            <version>???</version>
        </dependency>
    </dependencies>
</plugin>
 ```

There are other [configuration options](https://github.com/docker-java/docker-java#configuration), including via system environment here, which might be preferable if you have a number of builds using the plugin.

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


Expose container IP as maven properties
---

By default, the container ip will be exposed as maven properties. This would be helpful for integration test as it doesn't require bind the exposed port of container to a well know port of the host.

The property name is `docker.<CONTAINER ID>.ipAddress`, where <CONTAINER ID> is the folder name of where the Dockerfile is located. For example, by configuring `maven-failsafe-plugin` in the following way:

```<plugin>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>2.14.1</version>
                <configuration>
                    <systemPropertyVariables>
                        <example.app.ip>${docker.app.ipAddress}</example.app.ip>
                    </systemPropertyVariables>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>```

In the test, the ip address can be obtained by:
```
String host = System.getProperty("example.app.ip");
```

The plugin can also use the container ip address to do health check just by using `__CONTAINER.IP__` as a placeholder in the pings url. For example: 

```
healthChecks:
  pings:
    - url: http://__CONTAINER.IP__:8080/hello-world
      timeout: 60000
```

This can be turned off by set `exposeContainerIp` to `false` in `conf.yml`

