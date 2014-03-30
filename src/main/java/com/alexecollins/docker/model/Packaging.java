package com.alexecollins.docker.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.util.Collections.emptyList;

public class Packaging {
    @JsonProperty(required = false)
    public List<String> add = emptyList();
}
