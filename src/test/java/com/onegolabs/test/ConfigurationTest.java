package com.onegolabs.test;

import com.onegolabs.wamanager.Configuration;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * @author dmzhg
 */
public class ConfigurationTest {

    private static Configuration config;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUp() {
        config = new Configuration();
    }

    @Test
    public void initialization() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        config.init();
        Field customConfig = config.getClass().getDeclaredField("customConfig");
        customConfig.setAccessible(true);
        Object customPropsValue = customConfig.get(config);

        Field defaultConfig = config.getClass().getDeclaredField("defaultConfig");
        defaultConfig.setAccessible(true);
        Object defaultConfigValue = defaultConfig.get(config);

        assertNotNull(customPropsValue);
        assertNotNull(defaultConfigValue);
    }

    @Test
    public void shouldGetCustomInsteadOfDefaultProperty() throws NoSuchFieldException, IllegalAccessException {
        config.init();
        Field defaultConfig = config.getClass().getDeclaredField("defaultConfig");
        defaultConfig.setAccessible(true);
        Object defaultConfigValue = defaultConfig.get(config);
        assertNotSame(config.getProperty("test"), defaultConfigValue);
    }

    @Test
    public void shouldGetDefaultPropertyIfNoCustomFound() throws Exception {
        config.init();
        assertNotNull(config.getProperty("nullProperty"));
    }

    @Test
    public void recordConfigurationPropertyToFile() throws IOException {
        File testFile = testFolder.newFile("testProps.properties");
        FileWriter writer;
        config.init();
        writer = new FileWriter(testFile);
        config.store(writer, "Customer settings");
        assertTrue(testFile.length() > 0);
    }
}

