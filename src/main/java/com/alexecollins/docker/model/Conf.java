package com.alexecollins.docker.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.util.Collections.emptyList;

public class Conf {
    @JsonProperty(required = false)
    public List<Id> dependencies = emptyList();
    @JsonProperty(required = false)
    public Packaging packaging = new Packaging();
    @JsonProperty(required = false)
    public List<String> ports = emptyList();
}
