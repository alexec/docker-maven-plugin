package com.alexecollins.docker;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;


@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
@SuppressWarnings("unused")
public class CleanMojo extends AbstractDockerfilesMojo {

    @Override
    protected void doExecute(File dockerFolder, String tag) {
        // TODO
    }

    @Override
    protected String name() {
        return "build";
    }
}
