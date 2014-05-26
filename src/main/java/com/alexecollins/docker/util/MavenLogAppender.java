package com.alexecollins.docker.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.maven.plugin.logging.Log;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import static ch.qos.logback.classic.Level.*;

public class MavenLogAppender extends AppenderBase<ch.qos.logback.classic.spi.ILoggingEvent> {

	@SuppressWarnings("FieldCanBeLocal")
	private static Log LOG;

	static {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();
		Logger.getLogger("global").setLevel(java.util.logging.Level.FINEST);
	}

	public static void setLog(Log log) {
		MavenLogAppender.LOG = log;
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		if (eventObject.getLevel().isGreaterOrEqual(ERROR)) {
			LOG.error(eventObject.getMessage());
		} else if (eventObject.getLevel().isGreaterOrEqual(WARN)) {
			LOG.warn(eventObject.getMessage());
		} else if (eventObject.getLevel().isGreaterOrEqual(INFO)) {
			LOG.info(eventObject.getMessage());
		} else {
			LOG.debug(eventObject.getMessage());
		}
	}
}
