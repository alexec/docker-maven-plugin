package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.URI;

import static org.apache.commons.io.FileUtils.touch;


@Mojo(name = "version")
@SuppressWarnings("unused")
public class VersionMojo extends AbstractMojo {

    @Parameter(defaultValue = "http://127.0.0.1:4243")
    private URI host;
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    public void execute() throws MojoExecutionException {

        MavenLogAppender.setLog(getLog());

        final DockerClient dockerClient = new DockerClient(host.toString());
        try {
            getLog().info(dockerClient.version().toString());
            touch(new File(outputDirectory, "docker/version"));
        } catch (Exception e) {
            throw new MojoExecutionException("failed to get version", e);
        }

    }
}
