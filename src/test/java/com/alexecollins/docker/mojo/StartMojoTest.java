package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import com.github.dockerjava.api.DockerClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StartMojo.class)
public class StartMojoTest extends MojoTestSupport {

    private static Map<String,String> IP_ADDRESSES;

    private StartMojo startMojo;

    private DockerOrchestrator mockDockerOrchestrator;

    @BeforeClass
    public static void init() {

        IP_ADDRESSES = new HashMap<>();

        IP_ADDRESSES.put("ip1", "10.10.0.1");
        IP_ADDRESSES.put("ip2", "10.10.0.2");
        IP_ADDRESSES.put("ip3", "10.10.0.3");
        IP_ADDRESSES.put("ip4", "10.10.0.4");

        IP_ADDRESSES = Collections.unmodifiableMap(IP_ADDRESSES);
    }

    @Before
    public void setUp() throws Exception {

        startMojo = PowerMockito.spy(new StartMojo());
        DockerClient mockDockerClient = mock(DockerClient.class);

        mockDockerOrchestrator = mock(DockerOrchestrator.class);
        doReturn(IP_ADDRESSES).when(mockDockerOrchestrator).getIPAddresses();

        prepareMojo(startMojo, mockDockerClient, mockDockerOrchestrator);
    }

    @Test
    public void testStart() throws Exception {
        // when
        startMojo.execute();

        // then
        verify(startMojo).doExecute(mockDockerOrchestrator);
        verify(mockDockerOrchestrator).start();

        Properties projectProperties = startMojo.getProject().getProperties();

        assertEquals(IP_ADDRESSES.size(), projectProperties.size());

        for (String key : IP_ADDRESSES.keySet()) {
            assertEquals(IP_ADDRESSES.get(key), projectProperties.getProperty("docker." + key + ".ipAddress"));
        }
    }
}
