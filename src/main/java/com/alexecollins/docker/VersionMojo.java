package com.alexecollins.docker;

import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "version")
@SuppressWarnings("unused")
public class VersionMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute() throws Exception {
        getLog().info(docker.version().toString());
    }

    @Override
    protected String name() {
        return "version";
    }
}
