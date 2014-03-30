package com.alexecollins.docker;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.*;

abstract class AbstractDockersMojo extends AbstractDockerMojo {

    protected final void doExecute() throws Exception {

        final File dir = new File("src/main/docker");

        if (!dir.isDirectory()) {
            getLog().warn(dir.getAbsolutePath() + " does not exist, or is not dir, skipping");
            return;
        }

        for (File dockerFolder : dir.listFiles()) {
            final String name = dockerFolder.getName();
            final File destDir = new File(workDir, name);
            copyDirectory(dockerFolder, destDir);
            getLog().info(name() + " " + name);
            doExecute(destDir, tag(dockerFolder));
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

    protected String getContainerId(File dockerFolder) throws IOException {
        final File file = containerIdFile(dockerFolder);
        return file.exists() ? readFileToString(file) : null;
    }

    private File containerIdFile(File dockerFolder) {
        return new File(workDir, dockerFolder.getName() + "/containerId");
    }
}
