package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * Clean up containers, stopping them in necessary and there removing their images.
 */
@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
public class CleanMojo extends AbstractDockerMojo {

    /**
     * Clean container only in clean.
     */
    @Parameter(defaultValue = "false", property = "docker.cleanContainerOnly")
    private boolean cleanContainerOnly;

    /**
     * Clean with docker force flag
     */
    @Parameter(defaultValue = "false", property = "docker.forceClean")
    private boolean forceClean;


    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
        if (isCleanContainerOnly()) {
            orchestrator.cleanContainers(isForceClean());
        } else {
            orchestrator.clean(isForceClean());
        }
    }

    boolean isCleanContainerOnly() {
        return cleanContainerOnly;
    }

    void setCleanContainerOnly(boolean cleanContainerOnly) {
        this.cleanContainerOnly = cleanContainerOnly;
    }

    boolean isForceClean() {
        return forceClean;
    }

    void setForceClean(boolean forceClean) {
        this.forceClean = forceClean;
    }
}
