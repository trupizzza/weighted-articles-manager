import com.onegolabs.wamanager.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * @author dmzhg
 */
public class ConfigurationTest {

	private static Configuration config;

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
}

