package com.alexecollins.docker;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class AbstractDockersMojoTest {

    private AbstractDockersMojo sut = new AbstractDockersMojo() {
        @Override
        protected void doExecute(String name) throws Exception {

        }

        @Override
        protected String name() {
            return null;
        }
    };

    @Before
    public void setUp() throws Exception {
        sut.workDir = new File("target/docker");
    }

    @Test
    public void testImageIdStore() throws Exception {
        sut.storeImageId("alex", "alex1");

        assertEquals("alex1", sut.getImageId("alex"));
    }
}
