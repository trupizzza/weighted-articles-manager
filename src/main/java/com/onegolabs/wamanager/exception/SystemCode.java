package com.onegolabs.wamanager.exception;

/**
 * @author dmzhg
 */
public enum SystemCode implements ErrorCode {

    QUERY_FILE_NOT_FOUND(0),
    ERROR_WHILE_READING_FILE(1),
    ERROR_WHILE_CLOSING_FILE_INPUT_STREAM(2),
    QUERY_NAME_CANT_BE_NULL(3), BAD_VALUE(4);

    private final int code;

    SystemCode(int code) {
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
