package com.alexecollins.docker;

import com.alexecollins.docker.model.Conf;
import com.alexecollins.docker.model.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;

abstract class AbstractDockersMojo extends AbstractDockerMojo {


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
            links.put(id, conf(id).links);
        }

        for (Id id : sort(links)) {

            getLog().info(name() + " " + id);

            doExecute(id);
        }
    }

    protected Conf conf(Id id) throws IOException {
        return MAPPER.readValue(new File(src(id), "conf.yml"), Conf.class);
    }

    private File src() {
        return new File(project.getBasedir(), "src/main/docker");
    }

    static List<Id> sort(final Map<Id, List<Id>> deps) {
        final List<Id> in = new LinkedList<Id>(deps.keySet());
        final List<Id> out = new LinkedList<Id>();

        while (!in.isEmpty()) {
            boolean hit = false;
            for (Iterator<Id> iterator = in.iterator(); iterator.hasNext(); ) {
                final Id id = iterator.next();
                if (out.containsAll(deps.get(id))) {
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

    String name(Id id) {
        return prefix + "-" + id;
    }

    protected abstract void doExecute(Id name) throws Exception;

    protected void storeImageId(Id name, String imageId) throws IOException {
        write(imageIdFile(name), imageId);
    }

    protected String getImageId(Id name) throws IOException {
        return readFileToString(imageIdFile(name));
    }

    private File imageIdFile(Id name) {
        return new File(workDir, name + "/.imageId");
    }


    protected void storeContainerId(Id name, String imageId) throws IOException {
        write(containerIdFile(name), imageId);
    }

    protected String getContainerId(Id name) throws IOException {
        final File file = containerIdFile(name);
        return file.exists() ? readFileToString(file) : null;
    }

    private File containerIdFile(Id name) {
        return new File(workDir, name + "/.containerId");
    }
}
