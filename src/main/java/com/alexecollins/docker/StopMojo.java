package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StopMojo extends AbstractDockersMojo {

    @Override
    protected void doExecute(File dockerFolder, String tag) throws Exception {
        final String containerId = getContainerId(dockerFolder);
        if (containerId != null) {
            try {
                docker.stopContainer(containerId, 1);
            } catch (DockerException e) {
                getLog().warn(e);
                docker.kill(containerId);
            }
        } else {
            getLog().info(dockerFolder.getName() + " does not exist");
        }
    }

    @Override
    protected String name() {
        return "stop";
    }
}
