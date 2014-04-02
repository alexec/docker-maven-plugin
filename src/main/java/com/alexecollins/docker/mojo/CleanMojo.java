package com.alexecollins.docker.mojo;

import com.alexecollins.docker.model.Id;
import com.alexecollins.docker.task.CleanTask;
import com.alexecollins.docker.task.StopTask;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


/**
 * Clean up containers, stopping them in necessary and there removing their images.
 */
@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
public class CleanMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute() throws Exception {
        final StopTask stopTask = new StopTask(docker, repo);
        final CleanTask cleanTask = new CleanTask(docker, repo);
        for (Id id : repo.ids(true)) {
            stopTask.execute(id);
            cleanTask.execute(id);
        }
    }

}
