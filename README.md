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

```xml
<plugin>
    <groupId>com.alexecollins.docker</groupId>
    <artifactId>docker-maven-plugin</artifactId>
</plugin>
```

```sh
mvn docker:help
```

```sh
mvn docker:version
```