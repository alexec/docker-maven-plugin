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

import static org.apache.commons.io.FileUtils.touch;

abstract class AbstractDockerMojo extends AbstractMojo {
    /**
     * The host, e.g. -Ddocker.host=http://127.0.0.1:4243
     */
    @Parameter(defaultValue = "http://127.0.0.1:4243", property = "docker.host", required = true)
    private URI host;

    /**
     * A prefix to namespace scope machine. Important for isolating machines.
     */
    @Parameter(defaultValue = "${project.artifactId}", property = "docker.prefix", required = true)
    protected String prefix;

    @Component
    protected MavenProject project;

    protected DockerClient docker;
    protected File workDir;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        MavenLogAppender.setLog(getLog());

        try {
            docker = new DockerClient(host.toString());
            workDir = new File(project.getBuild().getDirectory(), "docker");
            doExecute();
            touch(new File(workDir, name()));
        } catch (Exception e) {
            throw new MojoExecutionException("failed", e);
        }
    }

    protected abstract void doExecute() throws Exception;

    protected abstract String name();
}
