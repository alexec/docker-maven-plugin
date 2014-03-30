package com.example.helloworld;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;

    @JsonProperty
    private long defaultId = 1;

    @Valid
    @NotNull
    @JsonProperty("database")
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public String getTemplate() {
        return template;
    }

    public long getDefaultId() {
        return defaultId;
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
}