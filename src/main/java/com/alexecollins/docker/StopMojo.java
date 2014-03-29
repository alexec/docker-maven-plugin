package com.alexecollins.docker;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;

@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StopMojo extends AbstractDockerfilesMojo {

    @Override
    protected void doExecute(File dockerFolder, String tag) throws Exception {
        try {
            docker.stopContainer(getContainer(dockerFolder), 5);
        } catch (DockerException e) {
            getLog().warn(e);
            docker.kill(getContainer(dockerFolder));
        }
    }

    @Override
    protected String name() {
        return "stop";
    }
}
