package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.orchestration.util.TextFileFilter;
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
import java.util.Properties;

abstract class AbstractDockerMojo extends AbstractMojo {

	/**
	 * The host, e.g. -Ddocker.host=http://127.0.0.1:4243
	 */
	@Parameter(defaultValue = DockerOrchestrator.DEFAULT_HOST, property = "docker.host")
	private URI host;

	/**
	 * A prefix to namespace scope machine. Important for isolating machines.
	 */
	@Parameter(defaultValue = "${project.artifactId}", property = "docker.prefix")
	private String prefix;

	/**
	 * Where to look for directories containing Dockerfile and conf.yml
	 */
	@Parameter(defaultValue = "src/main/docker", property = "docker.src")
	private String src;

	@Component
	private MavenProject project;

	@Override
	public final void execute() throws MojoExecutionException, MojoFailureException {
		MavenLogAppender.setLog(getLog());

		// not great eh
		final Properties properties = properties();

		getLog().info("properties filtering supported for " + properties.keySet());

		try {
			final DockerClient docker = new DockerClient(host.toString());
			getLog().info("Docker version " + docker.version().getVersion());
			doExecute(new DockerOrchestrator(docker, src(), workDir(), prefix, null, TextFileFilter.INSTANCE, properties));
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private Properties properties() {
		final Properties p = new Properties();

		final String[] x = new String[]{
				"project.groupId", project.getGroupId(),
				"project.artifactId", project.getArtifactId(),
				"project.version", project.getVersion(),
				"project.name", project.getName(),
				"project.description", project.getDescription(),
				"project.build.finalName", project.getBuild().getFinalName()
		};

		for (int i = 0; i < x.length; i += 2) {
			if (x[i + 1] != null) {
				p.setProperty(x[i], x[i + 1]);
			}
		}

		p.putAll(project.getProperties());

		return p;
	}

	private File workDir() {
		return new File(project.getBuild().getDirectory(), "docker");
	}

	private File src() {
		return new File(project.getBasedir(), src);
	}

	protected abstract void doExecute(DockerOrchestrator orchestrator) throws Exception;
}
