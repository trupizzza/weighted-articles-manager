package com.onegolabs.wamanager.exception.scales;

import com.onegolabs.wamanager.exception.ErrorCode;

/**
 * @author dmzhg
 */
public enum ScalesCommandCode implements ErrorCode {

    NO_CONNECTION_VIA_GIVEN_ADDRESS(0), IP_ADDRESS_NOT_DEFINED(1), COULDNT_CONNECT_TO_ADDRESS(3), FAILED_TO_CLEAR_PLU_AND_MSG(
            4), UNKNOWN(99), COULDNT_UPLOAD_ARTICLE(5);

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
