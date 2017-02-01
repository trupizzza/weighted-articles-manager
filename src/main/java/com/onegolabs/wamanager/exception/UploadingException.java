package com.onegolabs.wamanager.exception;

/**
 * @author dmzhg
 */
public class UploadingException extends BaseException {

    public UploadingException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UploadingException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UploadingException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public UploadingException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public UploadingException() {
        super();
    }

    public static UploadingException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof UploadingException) {
            UploadingException se = (UploadingException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new UploadingException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new UploadingException(exception.getMessage(), exception, errorCode);
        }
    }

    public static UploadingException wrap(Throwable exception) {
        return wrap(exception, null);
    }
}
