package com.onegolabs.wamanager;

import com.onegolabs.wamanager.exception.ConfigurationCode;
import com.onegolabs.wamanager.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Accumulating class for default and customer settings for app and scales
 *
 * @author dmzhg
 */
public class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final String DEFAULT_CONFIG_FILE_NAME = "defaultConfig.properties";
    private static final String SCALES_CONFIG_NAME = "scales.properties";

    private Properties customConfig;
    private Properties defaultConfig;
    private Properties scalesConfig;

    public void init() throws InitializationException {
        LOGGER.info("Loading default configuration...");
        this.defaultConfig = loadConfig(DEFAULT_CONFIG_FILE_NAME);
        LOGGER.info("Loading custom configuration...");
        this.customConfig = loadConfig(CONFIG_FILE_NAME);
        LOGGER.info("Loading scales configuration...");
        this.scalesConfig = loadConfig(SCALES_CONFIG_NAME);
    }

    public Properties getScalesConfig() {
        return scalesConfig;
    }

    private Properties getCustom() {
        return customConfig;
    }

    /**
     * Loads configuration file
     * If custom config has wrong property value or
     * doesn't have property/value at all - we'll take value from default config
     * Scales configuration don't have default values
     *
     * @return customer properties for app
     */
    private Properties loadConfig(String configFileName) throws InitializationException {
        Properties properties;
        InputStream input;
        try {
            input = this.getClass().getClassLoader().getResourceAsStream(configFileName);
            if (input == null) {
                // try to load from project folder
                input = new FileInputStream(configFileName);
            }
            if (configFileName.equals(CONFIG_FILE_NAME)) {
                // initialize defaults
                properties = new Properties(defaultConfig);
            } else {
                properties = new Properties();
            }
            properties.load(input);
            logProperties(configFileName, properties);
        } catch (FileNotFoundException e) {
            throw new InitializationException(e, ConfigurationCode.FILE_NOT_FOUND).set("fileName", configFileName);
        } catch (IOException e) {
            throw new InitializationException(e, ConfigurationCode.ERROR_WHILE_READING_FILE)
                    .set("fileName", configFileName);
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
        sb.append("--------------------").append("\r\n");
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            sb.append(key).append(" : ");
            sb.append(value).append("\r\n");
        }
        sb.append("--------------------").append("\r\n");
        LOGGER.debug(sb.toString());
    }

    public String getProperty(String key) {
        return this.getCustom().getProperty(key);
    }


    public void store(FileWriter fileWriter, String comment) throws IOException {
        getCustom().store(fileWriter, comment);
    }

    public void setProperty(String key, String value) {
        getCustom().setProperty(key, value);
    }

    public String getScalesProperty(String key) {
        return this.getScalesConfig().getProperty(key);
    }
}
