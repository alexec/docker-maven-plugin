package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StopMojo extends TearDownMojo {

    @Override
    protected void doExecute(Id id) throws Exception {
        for (Container container : findContainers(id)) {
            getLog().info(" - stop " + container.getId());
            try {
                docker.stopContainer(container.getId(), 1);
            } catch (DockerException e) {
                getLog().warn(e);
                docker.kill(container.getId());
            }
        }
    }
    @Override
    protected String name() {
        return "stop";
    }
}
