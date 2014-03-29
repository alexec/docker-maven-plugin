package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

import static org.apache.commons.io.FileUtils.touch;


@Mojo(name = "version")
@SuppressWarnings("unused")
public class VersionMojo extends AbstractDockerMojo {

    @Override
    void doExecute(DockerClient dockerClient, File workDir) throws Exception {
        getLog().info(dockerClient.version().toString());
        touch(new File(workDir, "version"));
    }
}
