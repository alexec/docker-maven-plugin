[![Build Status](https://drone.io/github.com/alexec/docker-maven-plugin/status.png)](https://drone.io/github.com/alexec/docker-maven-plugin/latest)

Docker Maven Plugin
===
Project Goal:

* Make it easy to build an app on a container, test it and push it to a Docker repository, even if it relies on other containers (e.g. a database)
* Talk "Maven" rather than "Docker" (E.g. "package" rather than "build").
* Keep it simple.

Pre-requisites
---
Docker installed and Docker daemon running, see the docker [getting started guide](https://www.docker.io/gettingstarted/).

Usage
---
The best example to look at is the [one from the tests](src/it/build-test-it) which creates a [Drop-Wizard](https://dropwizard.github.io/dropwizard/) app and builds three containers: __app__ (the dropwizard application) __data__ and __mysql__, and then runs an integration test against the deployed app. Et voila a packaged image!

Typically, you build your app, run your standard unit tests and package it as usual. Then, you build a container with your app deployed onto it, and run integration tests against it. If they pass, deploy your jar into the Maven repository, and optionally, your image into a Docker repository.

To use the plugin, you need to define a docker directory in ${basedir}/src/main which will include a subdirectory for each container that you wish to deploy.

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

TODO
---
* Filter resources to add properties (e.g ${project.version}).
* Set the name of the container.
* Wait for the service on the container to start.
* Add support for pushing tested containers.

Notes to self:

	docker ps -a -q | xargs docker rm

References
---
* http://www.alexecollins.com/tags/docker/
