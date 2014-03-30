package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.model.Container;
import com.kpelykh.docker.client.model.Image;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
@SuppressWarnings("unused")
public class CleanMojo extends StopMojo {

    @Override
    protected void doExecute(Id id) throws Exception {
        super.doExecute(id);

        final Container container = findContainer(id);
        if (container != null) {
            docker.removeContainer(container.getId());
        }
        final Image image = findImage(id);
        if (image != null) {
            docker.removeImage(image.getId());
        }
    }

    @Override
    protected String name() {
        return "clean";
    }
}
