[![Build Status](https://drone.io/github.com/alexec/docker-maven-plugin/status.png)](https://drone.io/github.com/alexec/docker-maven-plugin/latest)

Docker Maven Plugin
===

Project Goals:

* Run containers prior to integration tests, and tear-down afterwards.
* Package your app into a container.

Pre-requisites
---
Docker installed and Docker daemon running.

Usage
---
The best example to look at is the (src/it/build-test-it)[one from the tests] which creates a Drop-Wizard app and builds a container.