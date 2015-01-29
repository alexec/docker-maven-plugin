package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Validate {@code Dockerfile} files.
 * Based on
 * @see <a href="https://github.com/Runnable/validate-dockerfile">Npm validate-dockerfile</a>
 * and
 * @see <a href="https://docs.docker.com/articles/dockerfile_best-practices/">Best Practice</a>
 */
@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class ValidateMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) throws Exception {
        orchestrator.validate();
    }
}
