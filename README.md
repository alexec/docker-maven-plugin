[![Build Status](https://drone.io/github.com/alexec/docker-maven-plugin/status.png)](https://drone.io/github.com/alexec/docker-maven-plugin/latest)

Docker Maven Plugin
===
Project Goal:

* Make it easy to build an app on a container, test it and push it a Docker repository, even if it relies on other containers (e.g. a .database)
* Talk "Maven" rather than "Docker" (E.g. "package" rather than "build").
* Keep it simple.

Pre-requisites
---
Docker installed and Docker daemon running.

Usage
---
The best example to look at is [the one from the tests](src/it/build-test-it) which creates a Drop-Wizard app and builds a container, and then runs an integration test against it.

Typically, you build your app, run you standard unit tests and package it as usual. Then, you build a container with you app deployed onto it, and run integration tests against it. If they pass, deploy your jar into the Maven repository, and optionally, your image into a Docker repository.

TODO
---
* Add support for pushing tested containers.