package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.URI;

abstract class AbstractDockerMojo extends AbstractMojo {
    @Parameter(defaultValue = "http://127.0.0.1:4243")
    private URI host;

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        MavenLogAppender.setLog(getLog());

        try {
            doExecute(new DockerClient(host.toString()), new File(outputDirectory, "docker"));
        } catch (Exception e) {
            throw new MojoExecutionException("failed to get version", e);
        }
    }

    abstract void doExecute(DockerClient dockerClient, File workDir) throws Exception;

}
