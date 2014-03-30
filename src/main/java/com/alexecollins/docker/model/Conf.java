package com.alexecollins.docker.model;


import java.util.List;

import static java.util.Collections.emptyList;

public class Conf {
    public Packaging packaging = new Packaging();
    public List<String> ports = emptyList();
}
