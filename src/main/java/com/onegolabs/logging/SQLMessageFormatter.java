package com.onegolabs.logging;

/**
 * SQL logging strategy for use with p6spy logger
 * Use this class in spy.properties file as a value for 'logMessageFormat' property
 *
 * @author dmzhg
 */
public class SQLMessageFormatter implements com.p6spy.engine.spy.appender.MessageFormattingStrategy {

	@Override
	public String formatMessage(
			int connectionId, String now, long elapsed, String category, String prepared, String sql) {
		return "Duration: " + elapsed + "ms | SQL:" + prepared;
	}
}
