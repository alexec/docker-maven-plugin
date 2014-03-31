package com.alexecollins.docker.mojo;

import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.task.PackageTask;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


/**
 * Build containers from their {@code Dockerfile} and {@code conf.yml} files.
 */
@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
public class PackageMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute() throws Exception {

        final PackageTask task = new PackageTask(docker, repo, workDir);

        for (Id id : repo.ids(false)) {
            task.execute(id);
        }
    }
}
