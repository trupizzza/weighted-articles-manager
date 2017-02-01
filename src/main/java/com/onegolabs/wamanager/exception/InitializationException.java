package com.onegolabs.wamanager.exception;

/**
 * Class for handling exceptions during app initialize process
 * Handles configuration/context exceptions
 *
 * @author dmzhg
 */
public class InitializationException extends BaseException {

	public InitializationException(ErrorCode errorCode) {
		super(errorCode);
	}

	public InitializationException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public InitializationException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}

	public InitializationException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause, errorCode);
	}

	public InitializationException() {
		super();
	}

	public static InitializationException wrap(Throwable exception, ErrorCode errorCode) {
		if (exception instanceof InitializationException) {
			InitializationException se = (InitializationException) exception;
			if (errorCode != null && errorCode != se.getErrorCode()) {
				return new InitializationException(exception.getMessage(), exception, errorCode);
			}
			return se;
		} else {
			return new InitializationException(exception.getMessage(), exception, errorCode);
		}
	}

	public static InitializationException wrap(Throwable exception) {
		return wrap(exception, null);
	}
}
