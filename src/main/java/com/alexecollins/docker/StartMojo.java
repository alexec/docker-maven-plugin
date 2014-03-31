package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.*;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.util.List;

@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
@SuppressWarnings("unused")
public class StartMojo extends PackageMojo {

    @Override
    protected void doExecute(Id id) throws Exception {
        if (findImage(id) == null) {
            super.doExecute(id);
        }
        start(id);
    }

    private void start(Id id) throws DockerException, IOException {

        // only start
        if (findContainer(id) == null) {
            final ContainerConfig config = new ContainerConfig();
            config.setImage(findImage(id).getId());
            final ContainerCreateResponse response = docker.createContainer(config, imageName(id));
        }

        docker.startContainer(findContainer(id).getId(), newHostConfig(id));
    }

    private HostConfig newHostConfig(Id id) throws IOException {
        final HostConfig config = new HostConfig();

        config.setPublishAllPorts(true);
        config.setLinks(links(id));

        getLog().info(" - links " + conf(id).links);

        final Ports portBindings = new Ports();
        for (String e : conf(id).ports) {

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
            final String name = findContainer(links.get(i)).getNames()[0];
            out[i] = name + ":" + name;
        }

        return out;
    }

    @Override
    protected String name() {
        return "start";
    }
}
