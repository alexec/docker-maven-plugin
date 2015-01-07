package com.alexecollins.docker.mojo;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
    protected void doExecute(DockerOrchestrator orchestrator) {
        try {
            orchestrator.validate();
        } catch (Exception ex)
        {
            Logger.getLogger(ValidateMojo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
