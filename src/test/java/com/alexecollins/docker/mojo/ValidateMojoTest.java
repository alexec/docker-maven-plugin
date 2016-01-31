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
@PrepareForTest(ValidateMojo.class)
public class ValidateMojoTest extends MojoTestSupport {

    private ValidateMojo validateMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @Before
    public void setUp() throws Exception {

        validateMojo = PowerMockito.spy(new ValidateMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);
        mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(validateMojo, mockDockerClient, mockDockerOrchestrator);
    }

    @Test
    public void testValidate() throws Exception {
        // when
        validateMojo.execute();

        // then
        verify(validateMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).validate();
    }
}
