[![Build Status](https://drone.io/github.com/alexec/docker-maven-plugin/status.png)](https://drone.io/github.com/alexec/docker-maven-plugin/latest)

Docker Maven Plugin
===

Project Goals:

* Run containers prior to integration tests, and tear-down afterwards.
* Package your app into a container.

Usage:

```xml
<plugin>
    <groupId>com.alexecollins.docker</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>LATEST</version>
</plugin>
```

```sh
mvn docker:version
```