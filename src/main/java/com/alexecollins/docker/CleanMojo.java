package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;
import com.kpelykh.docker.client.model.Image;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.util.Arrays;


@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
@SuppressWarnings("unused")
public class CleanMojo extends StopMojo {

    @Override
    protected void doExecute(Id id) throws Exception {
        super.doExecute(id);

        clean(id);
    }

    private void clean(Id id) throws IOException, DockerException {
        for (Container container : findContainers(id, true)) {
            getLog().info(" - rm " + Arrays.toString(container.getNames()));
            docker.removeContainer(container.getId());
        }
        final Image image = findImage(id);
        if (image != null) {
            getLog().info(" - rmi " + image.getId());
            try {
                docker.removeImage(image.getId());
            } catch (DockerException e) {
                getLog().warn(" - " + e.getMessage());
            }
        }
    }

    @Override
    protected String name() {
        return "clean";
    }
}
