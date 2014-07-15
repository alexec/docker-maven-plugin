package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.orchestration.model.Credentials;
import com.alexecollins.docker.orchestration.util.TextFileFilter;
import com.alexecollins.docker.util.MavenLogAppender;
import com.kpelykh.docker.client.BuildFlag;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URI;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;

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

	/**
	 * Installed Docker version.
	 */
	@Parameter(property = "docker.version")
	private String version;

	/**
	 * Docker username (for deploy).
	 */
	@Parameter(property = "docker.username")
	private String username;

	/**
	 * Docker username (for deploy).
	 */
	@Parameter(property = "docker.password")
	private String password;

	/**
	 * Docker email (for deploy).
	 */
	@Parameter(property = "docker.email")
	private String email;

	/**
	 * Remove intermediate images during build.
	 */
	@Parameter(defaultValue = "false", property = "docker.removeIntermediateImages")
	private boolean removeIntermediateImages;

	/**
	 * Skip execution.
	 */
	@Parameter(defaultValue = "false", property = "docker.skip")
	private boolean skip;

	@Component
	private MavenProject project;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {

        MavenLogAppender.setLog(getLog());

	    if (skip) {
		    getLog().info("skipped");
	    }

        // not great eh
        final Properties properties = properties();

        getLog().info("properties filtering supported for " + properties.keySet());

        try {
            final DockerClient docker = dockerClient();
            getLog().info("Docker version " + docker.version().getVersion());
            doExecute(new DockerOrchestrator(docker, src(), workDir(), projDir(), prefix, credentials(),
                    TextFileFilter.INSTANCE, properties, buildFlags()));
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

	private Set<BuildFlag> buildFlags() {
		return removeIntermediateImages ? EnumSet.of(BuildFlag.REMOVE_INTERMEDIATE_IMAGES) : EnumSet.noneOf(BuildFlag.class);
	}

	private DockerClient dockerClient() throws DockerException {
        return version != null
                ? new DockerClient(host.toString(), version)
                : new DockerClient(host.toString());
    }

    private Credentials credentials() {
        return (username != null || password != null || email != null) ? new Credentials(username, password, email) : null;
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

    private File projDir() {
        return project.getBasedir();
    }

    private File src() {
        return new File(projDir(), src);
    }

    protected abstract void doExecute(DockerOrchestrator orchestrator) throws Exception;
}
