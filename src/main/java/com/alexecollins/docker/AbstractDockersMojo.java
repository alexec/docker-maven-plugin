package com.alexecollins.docker;

import com.alexecollins.docker.model.Conf;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;

;

abstract class AbstractDockersMojo extends AbstractDockerMojo {

    protected final void doExecute() throws Exception {

        final File dir = src();

        if (!dir.isDirectory()) {
            getLog().warn(dir.getAbsolutePath() + " does not exist, or is not dir, skipping");
            return;
        }

        for (Id id : ids()) {

            getLog().info(name() + " " + id);

            doExecute(id);
        }
    }

    private File src() {
        return new File(project.getBasedir(), "src/main/docker");
    }

    private List<Id> ids() {
        final List<Id> ids = new ArrayList<Id>();
        for (File file : src().listFiles()) {
            ids.add(new Id(file.getName()));
        }
        return ids;
    }

    File src(Id id) {
        return new File(src(), id.toString());
    }

    private static ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    Conf conf(Id name) throws IOException {
        final File confFile = new File(src(name), "conf.yml");
        return MAPPER.readValue(confFile, Conf.class);
    }

    String tag(Id name) {
        return prefix + "-" + name;
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
