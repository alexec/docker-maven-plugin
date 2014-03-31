package com.alexecollins.docker.mojo;

import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.task.PackageTask;
import com.alexecollins.docker.task.StartTask;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Start all the containers.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute() throws Exception {
        final PackageTask packageTask = new PackageTask(docker, repo, workDir);
        final StartTask startTask = new StartTask(docker, repo);
        for (Id id : repo.ids(false)) {
            if (repo.findImage(id) == null) {
                packageTask.execute(id);
            }
            startTask.execute(id);
        }
    }
}
