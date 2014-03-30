package com.alexecollins.docker;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class AbstractDockersMojoTest {

    private AbstractDockersMojo sut = new AbstractDockersMojo() {
        @Override
        protected void doExecute(Id name) throws Exception {

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
        final Id id = new Id("alex");
        sut.storeImageId(id, "alex1");

        assertEquals("alex1", sut.getImageId(id));
    }
}
