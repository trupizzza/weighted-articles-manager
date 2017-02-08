package com.onegolabs.resources;

import com.onegolabs.wamanager.exception.ErrorCode;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author dmzhg
 */
public class Messages {

    public static ResourceBundle r = ResourceBundle.getBundle("language", new Locale("ru"));
    public static ResourceBundle exR = ResourceBundle.getBundle("exceptions", new Locale("ru"));

    public static String getString(String key) {
        return r.getString(key);
    }

    public static String getExceptionText(ErrorCode code) {
        String key = code.getClass().getSimpleName() + "." + code;
        return exR.getString(key);
    }

    public static String getExceptionText(String key) {
        return exR.getString(key);
    }
}
