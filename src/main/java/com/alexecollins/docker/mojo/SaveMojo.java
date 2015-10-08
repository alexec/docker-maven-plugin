package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;


/**
 * Save images to tar/tar.gz files.
 */
@Mojo(name = "save", defaultPhase = LifecyclePhase.DEPLOY)
public class SaveMojo extends AbstractDockerMojo {
    /**
     * The directory to save images to.
     */
    @Parameter(defaultValue = "${project.build.dir}/docker", property = "docker.saveDir")
    private File saveDir;

    /**
     * Gzip saved images.
     */
    @Parameter(defaultValue = "false", property = "docker.gzipSave")
    private boolean gzipSave;

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
        orchestrator.save(saveDir, gzipSave);
    }
}
