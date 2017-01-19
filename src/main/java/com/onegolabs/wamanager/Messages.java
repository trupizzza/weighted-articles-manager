package com.onegolabs.wamanager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author dmzhg
 */
public class Messages {

	public static ResourceBundle r = ResourceBundle.getBundle("language", new Locale("ru"));

	public static String getString(String key) {
		return r.getString(key);
	}
}
