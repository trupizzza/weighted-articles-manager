package com.onegolabs.wamanager.exception;

import com.onegolabs.exception.BaseException;

/**
 * @author dmzhg
 */
public class SystemException extends BaseException {

    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SystemException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SystemException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public SystemException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public SystemException() {
        super();
    }

    public static SystemException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof SystemException) {
            SystemException se = (SystemException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new SystemException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new SystemException(exception.getMessage(), exception, errorCode);
        }
    }

    public static SystemException wrap(Throwable exception) {
        return wrap(exception, null);
    }
}
