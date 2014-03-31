package com.alexecollins.docker.task;

import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.component.Repo;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.apache.commons.io.FileUtils.copyFileToDirectory;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copyLarge;
import static org.apache.commons.lang.StringUtils.substringBetween;

public class PackageTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageTask.class);

    private final DockerClient docker;
    private final Repo repo;
    private final File workDir;

    public PackageTask(DockerClient docker, Repo repo, File workDir) {
        this.docker = docker;
        this.repo = repo;
        this.workDir = workDir;
    }

    public void execute(Id id) throws DockerException, IOException {
        LOGGER.info("package " + id);
        build(prepare(id), id);
    }

    private File prepare(Id id) throws IOException {
        final File dockerFolder = repo.src(id);
        final File destDir = new File(workDir, dockerFolder.getName());
        // copy template
        copyDirectory(dockerFolder, destDir);
        // copy files
        for (String file : repo.conf(id).packaging.add) {
            LOGGER.info(" - add " + file);
            copyFileToDirectory(new File(file), destDir);
        }

        return destDir;
    }

    private String build(File dockerFolder, Id name) throws DockerException, IOException {

        final ClientResponse response = docker.build(dockerFolder, repo.imageName(name));

        final StringWriter out = new StringWriter();
        try {
            copyLarge(new InputStreamReader(response.getEntityInputStream()), out);
        } finally {
            closeQuietly(response.getEntityInputStream());
        }

        String log = out.toString();
        if (!log.contains("Successfully built")) {
            throw new IllegalStateException("failed to build, log missing lines in" + log);
        }

        // imageId
        return substringBetween(log, "Successfully built ", "\\n\"}").trim();
    }

}
