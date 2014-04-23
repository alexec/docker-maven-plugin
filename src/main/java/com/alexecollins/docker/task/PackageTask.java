package com.alexecollins.docker.task;

import com.alexecollins.docker.component.Repo;
import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.sun.jersey.api.client.ClientResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import static org.apache.commons.io.FileUtils.copyDirectoryToDirectory;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.apache.commons.io.FileUtils.copyFileToDirectory;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copyLarge;

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
            File fileEntry = new File(file);
            copyFileEntry(destDir, fileEntry);
        }

        return destDir;
    }

    private void copyFileEntry(final File destDir, File fileEntry) throws IOException {
      if(fileEntry.isDirectory()) {
        LOGGER.info(" - add (dir) " + fileEntry.getAbsolutePath());
        copyDirectoryToDirectory(fileEntry, destDir); 
      }
      else {
        LOGGER.info(" - add (file) " + fileEntry.getAbsolutePath());
        copyFileToDirectory(fileEntry, destDir);
      }
    }
    

    @SuppressWarnings(("DM_DEFAULT_ENCODING"))
    private void build(File dockerFolder, Id id) throws DockerException, IOException {

        final ClientResponse response = docker.build(dockerFolder, repo.imageName(id));

        final StringWriter out = new StringWriter();
        try {
            copyLarge(new InputStreamReader(response.getEntityInputStream(), Charset.defaultCharset()), out);
        } finally {
            closeQuietly(response.getEntityInputStream());
        }

        String log = out.toString();
        if (!log.contains("Successfully built")) {
            throw new IllegalStateException("failed to build, log missing lines in" + log);
        }

        // imageId
        // return substringBetween(log, "Successfully built ", "\\n\"}").trim();
    }

}
