package com.alexecollins.docker.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;

/**
 */
public class ConfTest {
    private static ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @Test
    public void test() throws Exception {
        MAPPER.readValue(getClass().getResource("/conf.yml"), Conf.class);
    }
}
