package com.alexecollins.docker;

import com.alexecollins.docker.model.Conf;
import com.alexecollins.docker.model.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;
import com.kpelykh.docker.client.model.Image;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.*;

abstract class AbstractDockersMojo extends AbstractDockerMojo {
    protected final Map<Id,Conf> confs = new HashMap<Id, Conf>();

    protected final void doExecute() throws Exception {

        final File src = src();

        if (!src.isDirectory()) {
            getLog().warn(src.getAbsolutePath() + " does not exist, or is not dir, skipping");
            return;
        }

        final List<Id> in = new LinkedList<Id>();
        for (File file : src.listFiles()) {
            in.add(new Id(file.getName()));
        }

        final Map<Id, List<Id>> links = new HashMap<Id, List<Id>>();
        for (Id id : in) {
            confs.put(id, MAPPER.readValue(new File(src(id), "conf.yml"), Conf.class));
            links.put(id, confs.get(id).links);
        }

        for (Id id : sort(links)) {

            getLog().info(name() + " " + id);

            try {
                doExecute(id);
            } catch (Exception e) {
                throw new MojoExecutionException("failed to " + name() + " " + id, e);
            }
        }
    }

    private File src() {
        return new File(project.getBasedir(), "src/main/docker");
    }

    protected List<Id> sort(final Map<Id, List<Id>> links) {
        final List<Id> in = new LinkedList<Id>(links.keySet());
        final List<Id> out = new LinkedList<Id>();

        while (!in.isEmpty()) {
            boolean hit = false;
            for (Iterator<Id> iterator = in.iterator(); iterator.hasNext(); ) {
                final Id id = iterator.next();
                if (out.containsAll(links.get(id))) {
                    out.add(id);
                    iterator.remove();
                    hit = true;
                }
            }
            if (!hit) {
                throw new IllegalStateException("dependency error (e.g. circular dependency) amongst " + in);
            }
        }

        return out;
    }

    File src(Id id) {
        return new File(src(), id.toString());
    }

    private static ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    String imageName(Id id) {
        return prefix + "-" + id;
    }

    protected abstract void doExecute(Id name) throws Exception;

    protected Image findImage(Id id) throws DockerException {
        final List<Image> images = docker.getImages(imageName(id), true);
        return images.isEmpty() ? null : images.get(0);
    }

    protected List<Container> findContainers(Id id, boolean allContainers) throws IOException {
        final List<Container> strings = new ArrayList<Container>();
        for (Container container : docker.listContainers(allContainers)) {
            if (container.getImage().equals(imageName(id) + ":latest")) {
                strings.add(container);
            }
        }
        return strings;
    }

    protected Container findContainer(Id id) throws IOException {
        final List<Container> containerIds = findContainers(id, true);
        return containerIds.isEmpty() ? null : containerIds.get(0);
    }

}
