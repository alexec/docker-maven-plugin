package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.model.*;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.util.List;

@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StartMojo extends SetUpMojo {

    @Override
    protected void doExecute(Id id) throws Exception {
        final ContainerConfig containerConfig = new ContainerConfig();
        final Image imageId = findImage(id);
        if (imageId == null) {
            throw new IllegalStateException("unable to find image for " + id);
        }
        containerConfig.setImage(imageId.getId());
        final ContainerCreateResponse response = docker.createContainer(containerConfig);

        final String containerId = response.getId();
        docker.startContainer(containerId, newHostConfig(id));
    }

    private HostConfig newHostConfig(Id ids) throws IOException {
        final HostConfig config = new HostConfig();
        config.setPublishAllPorts(true);
        //config.setLinks(links(ids));
        final Ports portBindings = new Ports();
        for (String e : conf(ids).ports) {

            final String[] split = e.split(" ");

            assert split.length == 1 || split.length == 2;

            final int a = Integer.parseInt(split[0]);
            final int b = split.length == 2 ? Integer.parseInt(split[1]) : a;

            getLog().info(" - port " + e);
            portBindings.addPort(new Ports.Port("tcp", String.valueOf(a), null, String.valueOf(b)));
        }
        config.setPortBindings(portBindings);
        return config;
    }

    private String[] links(Id id) throws IOException {

        final List<Id> links = conf(id).links;
        final String[] out = new String[links.size()];
        for (int i = 0; i < links.size(); i++) {
            out[i] = links.get(i).toString();
        }

        return out;
    }

    @Override
    protected String name() {
        return "start";
    }
}
