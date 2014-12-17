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


        Map<String,String> nameANdIpMap =orchestrator.start();

        for(String containerName: nameANdIpMap.keySet()) {
            getProject().getProperties().setProperty(containerName.substring(1) + ".ipAddress", nameANdIpMap.get(containerName));
        }
    }

}
