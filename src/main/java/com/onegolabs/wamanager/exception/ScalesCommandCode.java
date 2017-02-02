package com.onegolabs.wamanager.exception;

/**
 * @author dmzhg
 */
public enum ScalesCommandCode implements ErrorCode {

    NO_CONNECTION_VIA_GIVEN_ADDRESS(0), IP_ADDRESS_NOT_DEFINED(1), NO_CONNECT_TO(3);

    private final int code;

    ScalesCommandCode(int code) {
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
