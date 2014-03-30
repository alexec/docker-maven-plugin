package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerException;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.apache.commons.io.FileUtils.copyFileToDirectory;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copyLarge;
import static org.apache.commons.lang.StringUtils.substringBetween;


@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
@SuppressWarnings("unused")
public class PackageMojo extends SetUpMojo {

    @Override
    protected void doExecute(final Id id) throws Exception {
        packag(id);
    }

    private void packag(Id id) throws DockerException, IOException {
        build(prepare(id), id);
    }

    private File prepare(Id name) throws IOException {
        final File dockerFolder = src(name);
        final File destDir = new File(workDir, dockerFolder.getName());
        // copy template
        copyDirectory(dockerFolder, destDir);
        // copy files
        for (String file : conf(name).packaging.add) {
            getLog().info(" - add " + file);
            copyFileToDirectory(new File(file), destDir);
        }

        return destDir;
    }

    private String build(File dockerFolder, Id name) throws DockerException, IOException {

        final ClientResponse response = docker.build(dockerFolder, imageName(name));

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

    @Override
    protected String name() {
        return "package";
    }
}
