package com.onegolabs.wamanager.exception.scales;

import com.onegolabs.exception.BaseException;
import com.onegolabs.wamanager.exception.ErrorCode;

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

    public static ScalesCommandException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof ScalesCommandException) {
            ScalesCommandException se = (ScalesCommandException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new ScalesCommandException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new ScalesCommandException(exception.getMessage(), exception, errorCode);
        }
    }

    public static ScalesCommandException wrap(Throwable exception) {
        return wrap(exception, null);
    }
}
