package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.util.MavenLogAppender;
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
	@Parameter(defaultValue = DockerOrchestrator.DEFAULT_HOST, property = "docker.host", required = true)
	private URI host;
	/**
	 * A prefix to namespace scope machine. Important for isolating machines.
	 */
	@Parameter(defaultValue = "${project.artifactId}", property = "docker.prefix", required = true)
	private String prefix;
	@Component
	private MavenProject project;

	@Override
	public final void execute() throws MojoExecutionException, MojoFailureException {
		MavenLogAppender.setLog(getLog());

		try {
			final DockerClient docker = new DockerClient(host.toString());
			getLog().info("Docker version " + docker.version().getApiVersion());
			doExecute(new DockerOrchestrator(docker, src(), workDir(), prefix));
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private File workDir() {
		return new File(project.getBuild().getDirectory(), "docker");
	}

	private File src() {
		return new File(project.getBasedir(), "src/main/docker");
	}

	protected abstract void doExecute(DockerOrchestrator orchestrator) throws Exception;
}
