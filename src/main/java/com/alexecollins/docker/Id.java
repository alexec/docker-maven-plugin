package com.alexecollins.docker;

public class Id {
    private final String value;

    public Id(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
