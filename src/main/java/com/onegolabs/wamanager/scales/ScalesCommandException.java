package com.onegolabs.wamanager.scales;

/**
 * @author dmzhg
 */
public class ScalesCommandException extends Exception {

    private int errorCode = 1;
    private long extendedErrorCode = 0L;

    public ScalesCommandException() {
        super();
    }

    public ScalesCommandException(String message) {
        super(message);
    }

    public ScalesCommandException(int errorCode, long extendedErrorCode) {
        super();
        this.errorCode = errorCode;
        this.extendedErrorCode = extendedErrorCode;
    }

    public ScalesCommandException(String message, int errorCode,
                                  long extendedErrorCode) {
        super(message);
        this.errorCode = errorCode;
        this.extendedErrorCode = extendedErrorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public long getExtendedErrorCode() {
        return extendedErrorCode;
    }

    @Override
    public String toString() {
        String str = super.toString();
        if (str == null) {
            str = "";
        }
        str += " [ErrorCode = " + errorCode + ", extendedErrorCode = "
                + extendedErrorCode + "]";
        return str;
    }
}