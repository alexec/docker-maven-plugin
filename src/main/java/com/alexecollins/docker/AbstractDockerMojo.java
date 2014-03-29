package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URI;

abstract class AbstractDockerMojo extends AbstractMojo {
    /**
     * The host, e.g. -Ddocker.host=http://127.0.0.1:4243
     */
    @Parameter(defaultValue = "http://127.0.0.1:4243", property = "docker.host")
    private URI host;
    @Component
    private MavenProject project;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        MavenLogAppender.setLog(getLog());

        try {
            doExecute(new DockerClient(host.toString()), new File(project.getBuild().getDirectory(), "docker"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException("failed", e);
        }
    }

    abstract void doExecute(DockerClient dockerClient, File workDir) throws Exception;

}
