package com.alexecollins.docker;

import org.junit.Test;

import java.net.URI;


public class AbstractDockerMojoTest {
    @Test
    public void testCreateDocker() throws Exception {
        AbstractDockerMojo.createDocker(URI.create(AbstractDockerMojo.DEFAULT_HOST));
    }
}
