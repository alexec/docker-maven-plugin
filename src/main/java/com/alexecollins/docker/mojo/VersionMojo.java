package com.alexecollins.docker.mojo;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "version")
@SuppressWarnings("unused")
public class VersionMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute() throws Exception {
        version();
    }

    private void version() throws DockerException {
        getLog().info(docker.version().toString());
    }

}
