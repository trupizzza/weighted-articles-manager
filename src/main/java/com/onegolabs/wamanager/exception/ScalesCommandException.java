package com.onegolabs.wamanager.exception;

/**
 * @author dmzhg
 */
public class ScalesCommandException extends BaseException {

    public ScalesCommandException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ScalesCommandException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ScalesCommandException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public ScalesCommandException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ScalesCommandException() {
        super();
    }
}
