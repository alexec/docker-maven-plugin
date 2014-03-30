package com.alexecollins.docker;

import com.alexecollins.docker.model.Id;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

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

    @Test
    public void testEmptyDependencies() throws Exception {
        assertEquals(Collections.<Id>emptyList(), AbstractDockersMojo.sortByDependencies(new HashMap<Id, List<Id>>()));
    }

    @Test
    public void testSingleDependencies() throws Exception {
        final Map<Id, List<Id>> deps = new HashMap<Id, List<Id>>();
        final Id a = new Id("a"), b = new Id("b");
        deps.put(b, Collections.singletonList(a));
        deps.put(a, Collections.<Id>emptyList());
        final ArrayList<Id> expected = new ArrayList<Id>();
        expected.add(a);
        expected.add(b);
        assertEquals(
                expected,
                AbstractDockersMojo.sortByDependencies(deps));
    }

    @Test
    public void testDoubleDependencies() throws Exception {
        final Map<Id, List<Id>> deps = new HashMap<Id, List<Id>>();
        final Id a = new Id("a"), b = new Id("b"), c = new Id("c");
        deps.put(c, Collections.singletonList(b));
        deps.put(b, Collections.singletonList(a));
        deps.put(a, Collections.<Id>emptyList());
        final ArrayList<Id> expected = new ArrayList<Id>();
        expected.add(a);
        expected.add(b);
        expected.add(c);
        assertEquals(
                expected,
                AbstractDockersMojo.sortByDependencies(deps));
    }

    @Test(expected = IllegalStateException.class)
    public void testCircularDependencies() throws Exception {
        final Map<Id, List<Id>> deps = new HashMap<Id, List<Id>>();
        final Id a = new Id("a"), b = new Id("b"), c = new Id("c"), d = new Id("d"), e = new Id("e");
        deps.put(c, Collections.singletonList(b));
        deps.put(b, Collections.singletonList(a));
        deps.put(a, Collections.singletonList(c));
        deps.put(d, Collections.singletonList(e));
        deps.put(e, Collections.<Id>emptyList());
        AbstractDockersMojo.sortByDependencies(deps);
    }

    @Test(expected = IllegalStateException.class)
    public void testSelfCircularDependencies() throws Exception {
        final Map<Id, List<Id>> deps = new HashMap<Id, List<Id>>();
        final Id a = new Id("a");
        deps.put(a, Collections.singletonList(a));
        AbstractDockersMojo.sortByDependencies(deps);
    }
}
