Contributing
===

Get the code:

```
mkdir docker
cd docker
git clone https://github.com/alexec/docker-java-orchestration.git
git clone https://github.com/alexec/docker-maven-plugin.git 
```

Create the config docker-java requires in ``/.docker.io.properties`:

```
docker.io.email=alex.e.c@gmail.com
docker.io.username=alexec
docker.io.password=yourpassword
```

Create this stub `pom.xml` so you can build all three at once:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.alexecollins.docker</groupId>
    <artifactId>docker-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <modules>
      <module>docker-java-orchestration</module>
      <module>docker-maven-plugin</module>
    </modules>
</project>
```

Set-up a port forward if you're using boot2docker:

```
VBoxManage controlvm boot2docker-vm natpf1 "8080,tcp,127.0.0.1,8080,,8080"
```

Now build with password:

```
mvn install -Prun-its -Ddocker.password=yourpassword -e
```

Tips
---
Tear down Docker:

	docker ps -a -q | xargs docker rm -fv
	docker images -a -q | xargs docker rmi -f
	
Remove port forward:

    VBoxManage controlvm boot2docker-vm natpf1 delete 8080
    
Contributors
---
* Alex Collins 
* alrighttheresham
* Christian Heineman
* Dan Jasek
* djsly
* Jacob Bay Hansen
* Lachlan Coote
* Laurie Hallowes
* Panu Wetterstrand
* Sam Morrison
* Vasco Figueira

