package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Copy resources from the containers.
 */
@Mojo(name = "copy", defaultPhase = LifecyclePhase.PACKAGE)
public class CopyMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) throws Exception {
        getLog().info("Copying " + getResource() + " to " + getTargetPath());
        orchestrator.copy(getResource(), getTargetPath());
    }
}
