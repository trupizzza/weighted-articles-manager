package com.onegolabs.resources;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author dmzhg
 */
public class Messages {

	public static ResourceBundle r = ResourceBundle.getBundle("language", new Locale("ru"));
//	public static ResourceBundle exR = ResourceBundle.getBundle("com.onegolabs.wamanager.exception.exceptions");

	public static String getString(String key) {
		return r.getString(key);
	}

	public static String getExceptionString(String key) {
		return "";
//		return exR.getString(key);
	}

//	public static String getExceptionText(ErrorCode code) {
//		return exR.getString(code.getClass().getSimpleName() + "." + code);
//	}
}
