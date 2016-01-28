package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.github.dockerjava.api.DockerClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CopyMojo.class)
public class CopyMojoTest extends MojoTestSupport {

    private static final String SOURCE = "source";

    private static final String DEST = "dest";

    private CopyMojo copyMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @Before
    public void setUp() throws Exception {

        copyMojo = PowerMockito.spy(new CopyMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);
        mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(copyMojo, mockDockerClient, mockDockerOrchestrator);
        Whitebox.setInternalState(copyMojo, "resource", SOURCE);
        Whitebox.setInternalState(copyMojo, "dest", DEST);
    }

    @Test
    public void testCopy() throws Exception {
        // when
        copyMojo.execute();

        // then
        verify(copyMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).copy(SOURCE, DEST);
    }

    @Test
    public void testGetters() {
        assertEquals(SOURCE, copyMojo.getSource());
        assertEquals(DEST, copyMojo.getDest());
    }
}

