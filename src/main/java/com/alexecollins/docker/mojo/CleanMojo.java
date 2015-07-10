package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


/**
 * Clean up containers, stopping them in necessary and there removing their images.
 */
@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
public class CleanMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
        if (isCleanContainerOnly()) {
            orchestrator.cleanContainers();
        } else {
            orchestrator.clean();
        }
    }
}
