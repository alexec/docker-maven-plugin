package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.orchestration.model.Id;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProjectHelper;

import java.io.File;
import java.util.Map;


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

    /**
     * Attach saved images to project for local install, or deployment to repository.
     */
    @Parameter(defaultValue = "false", property = "docker.attach")
    private boolean attach;

    @Component
    private MavenProjectHelper mavenProjectHelper;

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
        Map<Id, File> saved = orchestrator.save(saveDir, gzipSave);

        if (attach) {
            attachSavedFiles(saved);
        }
    }

    private void attachSavedFiles(Map<Id, File> saved) {
        for (Map.Entry<Id, File> save : saved.entrySet()) {
            File file = save.getValue();
            String extension = FilenameUtils.getExtension(file.getName());
            mavenProjectHelper.attachArtifact(getProject(), extension, file);
        }
    }
}
