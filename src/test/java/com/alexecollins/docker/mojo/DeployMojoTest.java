package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.github.dockerjava.api.DockerClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DeployMojo.class)
public class DeployMojoTest extends MojoTestSupport {

    private DeployMojo deployMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @Before
    public void setUp() throws Exception {

        deployMojo = PowerMockito.spy(new DeployMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);
        mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(deployMojo, mockDockerClient, mockDockerOrchestrator);
    }

    @Test
    public void testDeploy() throws Exception {
        // when
        deployMojo.execute();

        // then
        verify(deployMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).push();
    }
}
