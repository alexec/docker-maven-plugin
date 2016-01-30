package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.alexecollins.docker.orchestration.model.BuildFlag;
import com.github.dockerjava.api.DockerClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractDockerMojo.class)
public class AbstractDockerMojoTest extends MojoTestSupport {

    private AbstractDockerMojo dockerMojo;

    private DockerClient dockerClient;

    @Before
    public void setUp() throws Exception {
        dockerMojo = PowerMockito.spy(new AbstractDockerMojo() {
            @Override
            protected void doExecute(DockerOrchestrator orchestrator) throws Exception {
                // do nothing
            }
        });
        dockerClient = mock(DockerClient.class);

        prepareMojo(dockerMojo, dockerClient, null);
    }

    @Test
    public void testExecution() throws Exception {
        // given
        ArgumentCaptor<DockerOrchestrator> captor = ArgumentCaptor.forClass(DockerOrchestrator.class);

        // when
        dockerMojo.execute();

        // then
        verify(dockerMojo).doExecute(captor.capture());
        DockerOrchestrator capturedOrchestrator = captor.getValue();
        assertNotNull(capturedOrchestrator);

        assertEquals(dockerClient, Whitebox.getInternalState(capturedOrchestrator, "docker"));

        Set<BuildFlag> buildFlags = Whitebox.getInternalState(capturedOrchestrator, "buildFlags");
        assertNotNull(buildFlags);
        assertFalse(buildFlags.contains(BuildFlag.REMOVE_INTERMEDIATE_IMAGES));
        assertFalse(buildFlags.contains(BuildFlag.NO_CACHE));
        assertFalse(buildFlags.contains(BuildFlag.PULL));
        assertFalse(buildFlags.contains(BuildFlag.QUIET));
    }

    @Test
    public void testExecutionWithFlags() throws Exception {
        // given
        Whitebox.setInternalState(dockerMojo, "removeIntermediateImages", true);
        Whitebox.setInternalState(dockerMojo, "cache", false);
        Whitebox.setInternalState(dockerMojo, "quiet", true);
        Whitebox.setInternalState(dockerMojo, "pull", true);


        ArgumentCaptor<DockerOrchestrator> captor = ArgumentCaptor.forClass(DockerOrchestrator.class);

        // when
        dockerMojo.execute();
        
        // then
        verify(dockerMojo).doExecute(captor.capture());
        DockerOrchestrator capturedOrchestrator = captor.getValue();
        assertNotNull(capturedOrchestrator);

        assertEquals(dockerClient, Whitebox.getInternalState(capturedOrchestrator, "docker"));

        Set<BuildFlag> buildFlags = Whitebox.getInternalState(capturedOrchestrator, "buildFlags");
        assertNotNull(buildFlags);
        assertTrue(buildFlags.contains(BuildFlag.REMOVE_INTERMEDIATE_IMAGES));
        assertTrue(buildFlags.contains(BuildFlag.NO_CACHE));
        assertTrue(buildFlags.contains(BuildFlag.PULL));
        assertTrue(buildFlags.contains(BuildFlag.QUIET));
    }
    
    @Test
    public void testExecutionWithSkip() throws Exception {
        // given
        Whitebox.setInternalState(dockerMojo, "skip", true);

        // when
        dockerMojo.execute();

        // then
        verify(dockerMojo, never()).doExecute(any(DockerOrchestrator.class));
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecutionThrowsException() throws Exception {
        // given
        doThrow(Exception.class).when(dockerMojo).doExecute(any(DockerOrchestrator.class));

        // when
        dockerMojo.execute();

        // then
        fail("AbstractDockerMojo should throw MojoExecutionException when execution fails");
    }

}
