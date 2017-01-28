package com.onegolabs.wamanager.exception;

/**
 * Connection-related exceptions enum
 *
 * @author dmzhg
 */
public enum ConnectionCode implements ErrorCode {

	NO_CONNECTION(0),
	WRONG_DB_CHOSEN(1);

	private final int code;

	ConnectionCode(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Code" + ": " + code;
	}
}
