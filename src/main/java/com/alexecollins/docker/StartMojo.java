package com.alexecollins.docker;

import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StartMojo extends AbstractDockerfilesMojo {

    @Override
    protected void doExecute(File dockerFolder, String tag) throws Exception {
        final ContainerConfig containerConfig = new ContainerConfig();
        containerConfig.setImage(getImageId(dockerFolder));
        final ContainerCreateResponse response = docker.createContainer(containerConfig);

        final String containerId = response.getId();
        docker.startContainer(containerId);

        storeContainerId(dockerFolder, containerId);
    }

    @Override
    protected String name() {
        return "start";
    }
}
