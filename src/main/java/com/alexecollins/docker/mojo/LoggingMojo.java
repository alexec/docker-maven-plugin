package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Save log output of containers to a file.
 */
@Mojo(name = "save-logs", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class LoggingMojo extends AbstractDockerMojo {

    /**
     * The directory to save logs to.
     */
    @Parameter(defaultValue = "${project.build.directory}/docker", property = "docker.saveDir")
    private File saveDir;

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) throws Exception {
        orchestrator.saveLogs(saveDir);
    }
}
