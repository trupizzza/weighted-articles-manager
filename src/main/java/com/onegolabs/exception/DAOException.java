package com.onegolabs.exception;

import com.onegolabs.wamanager.exception.ErrorCode;

/**
 * @author dmzhg
 */
public class DAOException extends BaseException {

    public DAOException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DAOException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DAOException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public DAOException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DAOException() {
        super();
    }

    public static DAOException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof DAOException) {
            DAOException se = (DAOException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new DAOException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new DAOException(exception.getMessage(), exception, errorCode);
        }
    }

    public static DAOException wrap(Throwable exception) {
        return wrap(exception, null);
    }
}
