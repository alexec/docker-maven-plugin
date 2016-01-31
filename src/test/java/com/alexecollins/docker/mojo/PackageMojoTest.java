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
@PrepareForTest(PackageMojo.class)
public class PackageMojoTest extends MojoTestSupport {

    private PackageMojo packageMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @Before
    public void setUp() throws Exception {

        packageMojo = PowerMockito.spy(new PackageMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);
        mockDockerOrchestrator = mock(DockerOrchestrator.class);

        prepareMojo(packageMojo, mockDockerClient, mockDockerOrchestrator);
    }

    @Test
    public void testPackage() throws Exception {
        // when
        packageMojo.execute();

        // then
        verify(packageMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).build();
    }
}
