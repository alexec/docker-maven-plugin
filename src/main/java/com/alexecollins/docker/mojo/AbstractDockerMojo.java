package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.orchestration.ExcludeFilter;
import com.alexecollins.docker.orchestration.model.BuildFlag;
import com.alexecollins.docker.util.MavenLogAppender;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.DockerException;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

abstract class AbstractDockerMojo extends AbstractMojo {

    /**
     * The host, e.g. -Ddocker.host=http://127.0.0.1:2375
     */
    @Parameter(property = "docker.host")
    private String host;

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
    @Parameter(property = "docker.username", defaultValue = "${user.name}")
    private String username;

    /**
     * Docker username (for deploy).
     */
    @Parameter(property = "docker.password")
    private String password;

    /**
     * Docker repository server (for deploy).
     */
    @Parameter(property = "docker.serverAddress")
    private String serverAddress;

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
     * Use cached images during build.
     */
    @Parameter(defaultValue = "true", property = "docker.cache")
    private boolean cache = true;

    /**
     * Don't output the docker api responses during builds.
     */
    @Parameter(defaultValue = "false", property = "docker.quiet")
    private boolean quiet;

    /**
     * Skip execution.
     */
    @Parameter(defaultValue = "false", property = "docker.skip")
    private boolean skip;

    /**
     * Exclude certain definitions, e.g. to not start one container.
     *
     * Comma-separated.
     */
    @Parameter(defaultValue = "", property = "docker.exclude")
    private String exclude = "";

    /**
     * Specify docker certificate path. Defaults to not being set
     */
    @Parameter(property = "docker.certPath")
    private String certificatePath;

    @Component
    private MavenProject project;

    public MavenProject getProject() {
        return project;
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {

        MavenLogAppender.setLog(getLog());

        if (skip) {
            getLog().info("skipped");
            return;
        }

        // not great eh
        final Properties properties = properties();

        getLog().debug("properties filtering supported for " + properties.keySet());

        try {
            final DockerClient docker = dockerClient();
            getLog().info("Docker version " + docker.versionCmd().exec().getVersion());
            doExecute(dockerOrchestrator(properties, docker));
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private DockerOrchestrator dockerOrchestrator(Properties properties, DockerClient docker) {
        return DockerOrchestrator
                .builder()
                .docker(docker)
                .src(src())
                .workDir(workDir())
                .rootDir(projDir())
                .user(username)
                .project(prefix)
                .properties(properties)
                .buildFlags(buildFlags())
                .definitionFilter(new ExcludeFilter(exclude.split(",")))
                .build();
    }

    private Set<BuildFlag> buildFlags() {
        final Set<BuildFlag> buildFlags = new HashSet<BuildFlag>();
        if (removeIntermediateImages) {
            buildFlags.add(BuildFlag.REMOVE_INTERMEDIATE_IMAGES);
        }
        if (!cache) {
            buildFlags.add(BuildFlag.NO_CACHE);
        }
        if(quiet) {
            buildFlags.add(BuildFlag.QUIET);
        }
        return buildFlags;
    }

    private DockerClient dockerClient() throws DockerException {
        DockerClientConfig.DockerClientConfigBuilder builder = DockerClientConfig.createDefaultConfigBuilder();
        if (host != null) {
            builder = builder.withUri(host);
        }
        if (version != null) {
            builder = builder.withVersion(version);
        }
        if (username != null) {
            builder = builder.withUsername(username);
        }
        if (password != null) {
            builder = builder.withPassword(password);
        }
        if (email != null) {
            builder = builder.withEmail(email);
        }
        if (serverAddress != null) {
            builder = builder.withServerAddress(serverAddress);
        }
        if (certificatePath != null) {
            builder = builder.withDockerCertPath(certificatePath);
        }

        return DockerClientBuilder.getInstance(builder.build()).build();
    }


    private Properties properties() {
        final Properties p = new Properties();

        p.putAll(System.getenv());
        p.putAll(System.getProperties());

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
