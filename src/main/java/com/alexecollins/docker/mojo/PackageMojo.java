package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


/**
 * Build containers from their {@code Dockerfile} and {@code conf.yml} files.
 */
@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
public class PackageMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
	    orchestrator.build();
    }
}
