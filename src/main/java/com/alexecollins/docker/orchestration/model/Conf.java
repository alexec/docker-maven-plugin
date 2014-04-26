package com.alexecollins.docker.orchestration.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.util.Collections.emptyList;

@SuppressWarnings("CanBeFinal")
public class Conf {
    @JsonProperty(required = false)
    public List<Id> links = emptyList();
    @JsonProperty(required = false)
    public Packaging packaging = new Packaging();
    @JsonProperty(required = false)
    public List<String> ports = emptyList();
    @JsonProperty(required = false)
    public List<Id> volumesFrom = emptyList();
}
