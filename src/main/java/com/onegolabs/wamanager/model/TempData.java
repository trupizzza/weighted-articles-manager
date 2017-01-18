package com.onegolabs.wamanager.model;

/**
 * @author dmzhg
 */
public class TempData {
    int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    String name;

    public TempData(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
