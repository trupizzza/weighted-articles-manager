package com.onegolabs.exception;

import com.onegolabs.wamanager.exception.ErrorCode;

/**
 * @author dmzhg
 */
public enum DAOCode implements ErrorCode {

    SQL_ERROR(0);

    private final int code;

    DAOCode(int code) {
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
