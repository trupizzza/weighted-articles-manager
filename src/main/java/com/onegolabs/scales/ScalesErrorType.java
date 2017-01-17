package com.onegolabs.scales;

/**
 * @author dmzhg
 */
public enum ScalesErrorType {
    ERROR_SUCCESS(0),
    ERROR_SCALES(1),
    ERROR_OLE(3),
    ERROR_INTERNAL(4),
    ERROR_UNKNOWN(99);

    ScalesErrorType(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private int statusCode;
}
