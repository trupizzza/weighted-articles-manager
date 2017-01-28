package com.onegolabs.wamanager.exception;

/**
 * @author dmzhg
 */
public enum ConfigurationCode implements ErrorCode {
	FILE_NOT_FOUND(1), ERROR_WHILE_READING_FILE(2);

	private int code;

	ConfigurationCode(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}
}
