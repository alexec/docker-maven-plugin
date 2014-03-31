package com.alexecollins.docker.task;

import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.component.Repo;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.HostConfig;
import com.kpelykh.docker.client.model.Ports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class StartTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartTask.class);
    private final DockerClient docker;
    private final Repo repo;

    public StartTask(DockerClient docker, Repo repo) {
        this.docker = docker;
        this.repo = repo;
    }

    public void execute(Id id) throws DockerException, IOException {

        // only start
        if (repo.findContainer(id) == null) {
            LOGGER.info("creating container");
            final ContainerConfig config = new ContainerConfig();
            config.setImage(repo.findImage(id).getId());
            //config.setVolumesFrom(confs.get(id).volumesFrom.toString().replaceAll("[ \\[\\]]", ""));

            //LOGGER.info(" - volumes from " + confs.get(id).volumesFrom);

            docker.createContainer(config, repo.imageName(id));
        }

        LOGGER.info("starting " + id);
        docker.startContainer(repo.findContainer(id).getId(), newHostConfig(id));
    }

    private HostConfig newHostConfig(Id id) throws IOException {
        final HostConfig config = new HostConfig();

        config.setPublishAllPorts(true);
        config.setLinks(links(id));

        LOGGER.info(" - links " + repo.conf(id).links);

        final Ports portBindings = new Ports();
        for (String e : repo.conf(id).ports) {

            final String[] split = e.split(" ");

            assert split.length == 1 || split.length == 2;

            final int a = Integer.parseInt(split[0]);
            final int b = split.length == 2 ? Integer.parseInt(split[1]) : a;

            LOGGER.info(" - port " + e);
            portBindings.addPort(new Ports.Port("tcp", String.valueOf(a), null, String.valueOf(b)));
        }

        config.setPortBindings(portBindings);

        return config;
    }

    private String[] links(Id id) throws IOException {

        final List<Id> links = repo.conf(id).links;
        final String[] out = new String[links.size()];
        for (int i = 0; i < links.size(); i++) {
            final String name = repo.findContainer(links.get(i)).getNames()[0];
            out[i] = name + ":" + name;
        }

        return out;
    }}
