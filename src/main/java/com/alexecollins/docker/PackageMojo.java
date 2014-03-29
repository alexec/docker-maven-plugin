package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerException;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;


@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
@SuppressWarnings("unused")
public class PackageMojo extends AbstractDockerfilesMojo {

    @Override
    protected void doExecute(File dockerFolder, String tag) throws Exception {
        String imageId = build(dockerFolder, tag);

        storeImageId(dockerFolder, imageId);
    }

    private String build(File dockerFolder, String tag) throws DockerException, IOException {
        final ClientResponse response = docker.build(dockerFolder, tag);

        StringWriter logwriter = new StringWriter();

        try {
            LineIterator itr = IOUtils.lineIterator(response.getEntityInputStream(), "UTF-8");
            while (itr.hasNext()) {
                String line = itr.next();
                logwriter.write(line + "\n");
            }
        } finally {
            IOUtils.closeQuietly(response.getEntityInputStream());
        }

        String fullLog = logwriter.toString();
        if (!fullLog.contains("Successfully built")) {
            throw new IllegalStateException();
        }

        return StringUtils.substringBetween(fullLog, "Successfully built ", "\\n\"}").trim();
    }

    @Override
    protected String name() {
        return "package";
    }
}
