package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.github.dockerjava.api.DockerClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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

    @Before
    public void setUp() throws Exception {
        // prepare mojo
        dockerMojo = PowerMockito.spy(new AbstractDockerMojo() {
            @Override
            protected void doExecute(DockerOrchestrator orchestrator) throws Exception {
                // do nothing
            }
        });

        DockerClient mockDockerClient = mock(DockerClient.class);
        DockerOrchestrator mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(dockerMojo, mockDockerClient, mockDockerOrchestrator);
    }


    @Test
    public void testSkip() throws Exception {
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
