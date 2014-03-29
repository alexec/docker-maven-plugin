package com.alexecollins.docker;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;


@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
@SuppressWarnings("unused")
public class CleanMojo extends StopMojo {

    @Override
    protected String name() {
        return "clean";
    }
}
