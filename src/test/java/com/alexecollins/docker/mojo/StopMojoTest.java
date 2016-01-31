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
@PrepareForTest(StopMojo.class)
public class StopMojoTest extends MojoTestSupport {

    private StopMojo stopMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @Before
    public void setUp() throws Exception {

        stopMojo = PowerMockito.spy(new StopMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);
        mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(stopMojo, mockDockerClient, mockDockerOrchestrator);
    }

    @Test
    public void testStop() throws Exception {
        // when
        stopMojo.execute();

        // then
        verify(stopMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).stop();
    }
}
