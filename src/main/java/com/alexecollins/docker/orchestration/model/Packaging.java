package com.alexecollins.docker.orchestration.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.util.Collections.emptyList;

@SuppressWarnings("CanBeFinal")
public class Packaging {
    @JsonProperty(required = false)
    public List<String> add = emptyList();
}
