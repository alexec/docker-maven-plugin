Debugging
===

`docker:start` can be started manually like this:

    docker run -t -i -link example-project_mysql:db -P -p 8080:8080 alex.e.c/example-project-app:1.0-SNAPSHOT  

You can see the requests made to Docker using Wireshark. However, if you're using boot2docker, it'll be on a local loop-back interface. 

It can be useful to see what's running, and `watch` will help you:

    watch docker ps

You can tail the Docker logs, e.g.

```
boot2docker ssh
tail -f /var/log/docker.log
```