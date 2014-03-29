package com.alexecollins.docker;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.write;

abstract class AbstractDockerfilesMojo extends AbstractDockerMojo {

    protected final void doExecute() throws Exception {

        final File dir = new File("src/main/docker/dockerfiles");

        if (!dir.isDirectory()) {
            getLog().warn(dir.getAbsolutePath() + " does not exist, or is not dir, skipping");
            return;
        }

        for (File dockerFolder : dir.listFiles()) {
            getLog().info(name() + " " + dockerFolder.getName());
            doExecute(dockerFolder, tag(dockerFolder));
        }
    }

    private String tag(File dockerFolder) {
        return prefix + "-" + dockerFolder.getName();
    }

    protected abstract void doExecute(File dockerFolder, String tag) throws Exception;

    protected void storeImageId(File dockerFolder, String imageId) throws IOException {
        write(imageIdFile(dockerFolder), imageId);
    }

    protected String getImageId(File dockerFolder) throws IOException {
        return readFileToString(imageIdFile(dockerFolder));
    }

    private File imageIdFile(File dockerFolder) {
        return new File(workDir, dockerFolder.getName() + "/imageId");
    }


    protected void storeContainerId(File dockerFolder, String imageId) throws IOException {
        write(containerIdFile(dockerFolder), imageId);
    }

    protected String getContainer(File dockerFolder) throws IOException {
        return readFileToString(containerIdFile(dockerFolder));
    }

    private File containerIdFile(File dockerFolder) {
        return new File(workDir, dockerFolder.getName() + "/containerId");
    }
}
