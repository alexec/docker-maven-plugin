package com.alexecollins.docker.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.maven.plugin.logging.Log;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MavenLogAppender extends AppenderBase<ch.qos.logback.classic.spi.ILoggingEvent> {

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
	protected void append(ILoggingEvent eventObject) {
		if (eventObject.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.ERROR)) {
			log.error(eventObject.getMessage());
		} else if (eventObject.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.WARN)) {
			log.warn(eventObject.getMessage());
		} else if (eventObject.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.INFO)) {
			log.info(eventObject.getMessage());
		} else {
			log.debug(eventObject.getMessage());
		}
	}
}
