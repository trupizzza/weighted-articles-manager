import com.onegolabs.wamanager.Configuration;
import junit.framework.TestCase;

/**
 * @author dmzhg
 */
public class ConfigurationTest extends TestCase {

	private Configuration config;

	@Override
	protected void setUp() {
		config = new Configuration();
	}

	public void testLoadDefaultConfig() {
		config.init();
		assertNotNull(config.getDefault());
	}

	public void testLoadCustomConfig() {
		config.init();
		assertNotNull(config.getCustom());
	}
}

