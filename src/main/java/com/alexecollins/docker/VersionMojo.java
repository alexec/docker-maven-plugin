package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.net.URI;


@Mojo(name = "version")
@SuppressWarnings("unused")
public class VersionMojo extends AbstractMojo {

    @Parameter(defaultValue = "http://127.0.0.1:4243")
    private URI host;

    public void execute() throws MojoExecutionException {
        final DockerClient dockerClient = new DockerClient(host.toString());
        try {
            System.out.println(dockerClient.version());
        } catch (DockerException e) {
            throw new MojoExecutionException("failed to get version", e);
        }
    }
}
