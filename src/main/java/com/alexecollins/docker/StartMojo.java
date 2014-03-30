package com.alexecollins.docker;

import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StartMojo extends AbstractDockersMojo {

    @Override
    protected void doExecute(String name) throws Exception {
        final ContainerConfig containerConfig = new ContainerConfig();
        containerConfig.setImage(getImageId(name));
        final ContainerCreateResponse response = docker.createContainer(containerConfig);

        final String containerId = response.getId();
        docker.startContainer(containerId);

        storeContainerId(name, containerId);
    }

    @Override
    protected String name() {
        return "start";
    }
}
