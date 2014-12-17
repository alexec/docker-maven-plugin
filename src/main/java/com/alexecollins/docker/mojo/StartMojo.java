package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Map;

/**
 * Start all the containers.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
        orchestrator.start();
        Map<String, String> idANdIpMap = orchestrator.getIPAddresses();
        for (String id : idANdIpMap.keySet()) {
            getProject().getProperties().setProperty("docker." + id + ".ipAddress", idANdIpMap.get(id));
        }
    }

}
