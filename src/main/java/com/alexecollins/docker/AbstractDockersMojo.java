package com.alexecollins.docker;

import com.alexecollins.docker.model.Conf;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;

;

abstract class AbstractDockersMojo extends AbstractDockerMojo {

    protected final void doExecute() throws Exception {

        final File dir = new File("src/main/docker");

        if (!dir.isDirectory()) {
            getLog().warn(dir.getAbsolutePath() + " does not exist, or is not dir, skipping");
            return;
        }

        for (File dockerFolder : dir.listFiles()) {
            final String name = dockerFolder.getName();

            getLog().info(name() + " " + name);

            doExecute(name);
        }
    }

    private static ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    static Conf conf(File dockerFolder) throws IOException {
        final File confFile = new File(dockerFolder, "conf.yml");
        return MAPPER.readValue(confFile, Conf.class);
    }

    private String tag(File dockerFolder) {
        return prefix + "-" + dockerFolder.getName();
    }

    protected abstract void doExecute(String name) throws Exception;

    protected void storeImageId(String name, String imageId) throws IOException {
        write(imageIdFile(name), imageId);
    }

    protected String getImageId(String name) throws IOException {
        return readFileToString(imageIdFile(name));
    }

    private File imageIdFile(String name) {
        return new File(workDir, name + "/.imageId");
    }


    protected void storeContainerId(String name, String imageId) throws IOException {
        write(containerIdFile(name), imageId);
    }

    protected String getContainerId(String name) throws IOException {
        final File file = containerIdFile(name);
        return file.exists() ? readFileToString(file) : null;
    }

    private File containerIdFile(String name) {
        return new File(workDir, name + "/.containerId");
    }
}
