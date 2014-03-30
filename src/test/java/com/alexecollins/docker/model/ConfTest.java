package com.alexecollins.docker.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 */
public class ConfTest {
    private static ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @Test
    public void test() throws Exception {
        final Conf conf = MAPPER.readValue(getClass().getResource("/conf.yml"), Conf.class);

        assertNotNull(conf.links);
        assertNotNull(conf.packaging);
        assertNotNull(conf.ports);
    }
}
