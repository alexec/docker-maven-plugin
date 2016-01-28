package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.github.dockerjava.api.DockerClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CleanMojo.class)
public class CleanMojoTest extends MojoTestSupport {

    private CleanMojo cleanMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @Before
    public void setUp() throws Exception {

        cleanMojo = PowerMockito.spy(new CleanMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);
        mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(cleanMojo, mockDockerClient, mockDockerOrchestrator);
    }

    @Test
    public void testCleanAll() throws Exception {
        // when
        cleanMojo.execute();

        // then
        verify(cleanMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator, never()).cleanContainers();
        verify(mockDockerOrchestrator).clean();
    }

    @Test
    public void testCleanContainers() throws MojoFailureException, MojoExecutionException {
        // given
        Whitebox.setInternalState(cleanMojo, "cleanContainerOnly", true);

        // when
        cleanMojo.execute();

        // then
        verify(cleanMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).cleanContainers();
        verify(mockDockerOrchestrator, never()).clean();
    }
}
