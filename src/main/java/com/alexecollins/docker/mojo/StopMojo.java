package com.alexecollins.docker.mojo;

import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.task.StopTask;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Stop all the containers.
 */
@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class StopMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute() throws Exception {
        final StopTask stopTask = new StopTask(docker, repo);
        for (Id id : repo.ids(true)) {
            stopTask.execute(id);
        }
    }
}
