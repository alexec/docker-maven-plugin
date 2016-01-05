package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Copy resources from the containers.
 */
@Mojo(name = "copy", defaultPhase = LifecyclePhase.PACKAGE)
public class CopyMojo extends AbstractDockerMojo {
    /**
     * Resource to copy out of the docker container.
     */
    @Parameter(property = "docker.source")
    private String resource;

    /**
     * Resource to copy out of the docker container.
     */
    @Parameter(property = "docker.dest")
    private String dest;

    public String getSource() {
        return resource;
    }

    public String getDest() {
        return dest;
    }

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) throws Exception {
        getLog().info("Copying " + getSource() + " to " + getDest());
        orchestrator.copy(getSource(), getDest());
    }
}
