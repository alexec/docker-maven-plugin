package com.alexecollins.docker.util;

import ch.qos.logback.core.AppenderBase;
import org.apache.maven.plugin.logging.Log;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MavenLogAppender<E> extends AppenderBase<E> {

    @SuppressWarnings("FieldCanBeLocal")
    private static Log log;

	static {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();
		Logger.getLogger("global").setLevel(Level.FINEST);
	}

    public static void setLog(Log log) {
        MavenLogAppender.log = log;
    }

    @Override
    protected void append(E eventObject) {
        log.info(eventObject.toString());
    }
}
