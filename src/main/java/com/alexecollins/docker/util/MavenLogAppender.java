package com.alexecollins.docker.util;

import ch.qos.logback.core.AppenderBase;
import org.apache.maven.plugin.logging.Log;

public class MavenLogAppender<E> extends AppenderBase<E> {

    private static Log log;

    public static void setLog(Log log) {
        MavenLogAppender.log = log;
    }

    @Override
    protected void append(E eventObject) {
        //log.info(eventObject.toString())
        // ;
    }
}
