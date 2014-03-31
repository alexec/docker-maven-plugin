package com.alexecollins.docker.task;


import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.component.Repo;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;
import com.kpelykh.docker.client.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class CleanTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanTask.class);
    private final DockerClient docker;
    private final Repo repo;

    public CleanTask(DockerClient docker, Repo repo) {
        this.docker = docker;
        this.repo = repo;
    }

    public void execute(Id id) throws IOException, DockerException {
        LOGGER.info("clean " + id);
        for (Container container : repo.findContainers(id, true)) {
            LOGGER.info(" - rm " + Arrays.toString(container.getNames()));
            docker.removeContainer(container.getId());
        }
        final Image image = repo.findImage(id);
        if (image != null) {
            LOGGER.info(" - rmi " + image.getId());
            try {
                docker.removeImage(image.getId());
            } catch (DockerException e) {
                LOGGER.warn(" - " + e.getMessage());
            }
        }
    }

}
