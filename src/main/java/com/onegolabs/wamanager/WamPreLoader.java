package com.onegolabs.wamanager;

import javafx.application.Preloader;
import javafx.stage.Stage;

/**
 * @author dmzhg
 */
public class WamPreLoader extends Preloader {

	private static Configuration settings;

	public static Configuration getConfiguration() {
		return settings;
	}

	@Override
	public void start(Stage stage) throws Exception {
		settings = new Configuration();
		settings.init();
	}
}
