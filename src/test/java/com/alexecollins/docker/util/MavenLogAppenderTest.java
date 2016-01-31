package com.alexecollins.docker.util;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class MavenLogAppenderTest {

    private static final String ERROR_MESAGE = "error message";

    private static final String WARN_MESAGE = "warn message";

    private static final String INFO_MESAGE = "info message";

    private static final String DEBUG_MESAGE = "debug message";

    private static final String TRACE_MESSAGE = "trace message";

    private static final String ALL_MESSAGE = "all message";


    private static Log log;

    private MavenLogAppender mavenLogAppender;

    @Before
    public void setUp() {
        log = mock(Log.class);
        MavenLogAppender.setLog(log);

        mavenLogAppender = new MavenLogAppender();
    }

    @Test
    public void testErrorLogLevel() {
        // given
        ILoggingEvent event = createLoggingEvent(Level.ERROR, ERROR_MESAGE);

        // when
        mavenLogAppender.append(event);

        // then
        verify(log).error(ERROR_MESAGE);
        verify(log, never()).warn(anyString());
        verify(log, never()).info(anyString());
        verify(log, never()).debug(anyString());
    }

    @Test
    public void testWarnLogLevel() {
        // given
        ILoggingEvent event = createLoggingEvent(Level.WARN, WARN_MESAGE);

        // when
        mavenLogAppender.append(event);

        // then
        verify(log, never()).error(anyString());
        verify(log).warn(WARN_MESAGE);
        verify(log, never()).info(anyString());
        verify(log, never()).debug(anyString());
    }

    @Test
    public void testInfoLogLevel() {
        // given
        ILoggingEvent event = createLoggingEvent(Level.INFO, INFO_MESAGE);

        // when
        mavenLogAppender.append(event);

        // then
        verify(log, never()).error(anyString());
        verify(log, never()).warn(anyString());
        verify(log).info(INFO_MESAGE);
        verify(log, never()).debug(anyString());
    }

    @Test
    public void testDebugLogLevel() {
        // given
        ILoggingEvent event = createLoggingEvent(Level.DEBUG, DEBUG_MESAGE);

        // when
        mavenLogAppender.append(event);

        // then
        verify(log, never()).error(anyString());
        verify(log, never()).warn(anyString());
        verify(log, never()).info(anyString());
        verify(log).debug(eq(DEBUG_MESAGE));
    }

    @Test
    public void testTraceLogLevel() {
        // given
        ILoggingEvent event = createLoggingEvent(Level.TRACE, TRACE_MESSAGE);

        // when
        mavenLogAppender.append(event);

        // then
        verify(log, never()).error(anyString());
        verify(log, never()).warn(anyString());
        verify(log, never()).info(anyString());
        verify(log).debug(TRACE_MESSAGE);
    }

    @Test
    public void testAllLogLevel() {
        // given
        ILoggingEvent event = createLoggingEvent(Level.ALL, ALL_MESSAGE);

        // when
        mavenLogAppender.append(event);

        // then
        verify(log, never()).error(anyString());
        verify(log, never()).warn(anyString());
        verify(log, never()).info(anyString());
        verify(log).debug(ALL_MESSAGE);
    }

    private ILoggingEvent createLoggingEvent(Level level, String message) {
        ILoggingEvent event = mock(LoggingEvent.class);

        doReturn(level).when(event).getLevel();
        doReturn(message).when(event).getMessage();

        return event;
    }
}
