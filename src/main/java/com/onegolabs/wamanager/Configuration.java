package com.onegolabs.wamanager;

import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Accumulating class for default and customer settings for app
 *
 * @author dmzhg
 */
public class Configuration {

	private static final String CONFIG_FILE_NAME = "customConfig.properties";
	private static final String DEFAULT_CONFIG_FILE_NAME = "defaultConfig.properties";

	private static Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private Properties customConfig;
	private Properties defaultConfig;

	public void init() {
		this.defaultConfig = loadDefault();
		this.customConfig = loadCustom();
	}

	public Properties getCustom() {
		return customConfig;
	}

	public void setCustom(Properties config) {
		this.customConfig = config;
	}

	public Properties getDefault() {
		return defaultConfig;
	}

	public void setDefault(Properties config) {
		this.defaultConfig = config;
	}

	/**
	 * Loads default config bundled with app
	 * If custom config has wrong property value or
	 * doesn't have property/value at all - we'll take value from default config
	 *
	 * @return default properties for app
	 */
	private Properties loadDefault() {
		Properties properties = null;
		try {
			InputStream input = WAManager.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE_NAME);
			properties = new Properties();
			properties.load(input);
			logProperties(DEFAULT_CONFIG_FILE_NAME, properties);
		} catch (FileNotFoundException e) {
			showErrorMessage();
		} catch (IOException e) {
			LOGGER.error("Exception occurred while reading customConfig file");
		}
		return properties;
	}


	/**
	 * Loads customer configuration file
	 * File name must be 'config.properties'
	 * If custom config has wrong property value or
	 * doesn't have property/value at all - we'll take value from default config
	 *
	 * @return customer properties for app
	 */
	private Properties loadCustom() {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(new FileInputStream("999"));
			logProperties(CONFIG_FILE_NAME, properties);
		} catch (FileNotFoundException e) {
			LOGGER.error("Error while loading configuration file! " + e.getMessage());
			LOGGER.error("File name :" + CONFIG_FILE_NAME);
			LOGGER.error(e.toString());
			showErrorMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * Log properties information
	 *
	 * @param configFileName name of file from which properties were read
	 * @param properties     properties to log
	 */
	private void logProperties(String configFileName, Properties properties) {
		StringBuilder sb = new StringBuilder();
		sb.append("Application properties list [property:value] for file : ");
		sb.append(configFileName).append("\r\n");
		sb.append("----------").append("\r\n");
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			sb.append(key).append(" : ");
			sb.append(value).append("\r\n");
		}
		sb.append("----------").append("\r\n");
		LOGGER.debug(sb.toString());
	}

	private void showErrorMessage() {
		String title = Messages.getString("errorLoadConfigTitle");
		String message = Messages.getString("errorLoadConfigMessage");

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
		System.exit(1);
	}
}
