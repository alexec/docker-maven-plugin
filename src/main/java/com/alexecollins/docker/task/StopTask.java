package com.alexecollins.docker.task;

import com.alexecollins.docker.component.Repo;
import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class StopTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageTask.class);
    private final DockerClient docker;
    private final Repo repo;

    public StopTask(DockerClient docker, Repo repo) {
        this.docker = docker;
        this.repo = repo;
    }

    public void execute(Id id) throws DockerException {
        for (Container container : repo.findContainers(id, false)) {
            LOGGER.info("stopping " + Arrays.toString(container.getNames()));
            docker.stopContainer(container.getId(), 1);
        }
    }

}
