package com.alexecollins.docker;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class AbstractDockerfilesMojoTest {

    private AbstractDockerfilesMojo sut = new AbstractDockerfilesMojo() {
        @Override
        protected void doExecute(File dockerFolder, String tag) throws Exception {

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
        sut.storeImageId(new File("."), "alex");

        assertEquals("alex", sut.getImageId(new File(".")));

    }
}
