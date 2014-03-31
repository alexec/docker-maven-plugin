package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.util.Arrays;

/**
 * Stop all the containers.
 */
@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class StopMojo extends TearDownMojo {

    @Override
    protected void doExecute(Id id) throws Exception {
        stop(id);
    }

    private void stop(Id id) throws IOException, DockerException {
        for (Container container : findContainers(id, false)) {
            getLog().info("stopping " + Arrays.toString(container.getNames()));
            docker.stopContainer(container.getId(), 1);
        }
    }

    @Override
    protected String name() {
        return "stop";
    }
}
