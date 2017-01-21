package com.onegolabs.wamanager;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Accumulating class for default and customer settings for app
 *
 * @author dmzhg
 */
public class Configuration {

	private static final String CONFIG_FILE_NAME = "config.properties";
	private static final String DEFAULT_CONFIG_FILE_NAME = "defaultConfig.properties";
	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private Properties customConfig;
	private Properties defaultConfig;

	public void init() {
		LOGGER.info("Loading default config");
		this.defaultConfig = loadConfig(DEFAULT_CONFIG_FILE_NAME);
		LOGGER.info("Loading custom config");
		this.customConfig = loadConfig(CONFIG_FILE_NAME);
	}

	public Properties getCustom() {
		return customConfig;
	}

	public Properties getDefault() {
		return defaultConfig;
	}

	/**
	 * Loads configuration file
	 * If custom config has wrong property value or
	 * doesn't have property/value at all - we'll take value from default config
	 *
	 * @return customer properties for app
	 */
	private Properties loadConfig(String configFileName) {
		Properties properties = null;
		InputStream input;
		try {
			input = this.getClass().getClassLoader().getResourceAsStream(configFileName);
			if (input == null) {
				// try to load from project folder
				input = new FileInputStream(configFileName);
			}
			properties = new Properties();
			properties.load(input);
			logProperties(configFileName, properties);
		} catch (FileNotFoundException e) {
			LOGGER.error("Error while loading configuration file " + configFileName, e);
			showErrorMessage(e);
		} catch (IOException e) {
			e.printStackTrace();
			showErrorMessage(e);
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

	private void showErrorMessage(Exception ex) {
		String title = Messages.getString("errorLoadConfigTitle");
		String message = Messages.getString("errorLoadConfigMessage");

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText(title);
		alert.setContentText(message);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		String exceptionLabelError = Messages.getString("exceptionLabelError");
		Label label = new Label(exceptionLabelError);

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setExpanded(true);
		alert.showAndWait();
		System.exit(1);
	}
}
