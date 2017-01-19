import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.WAManager;
import junit.framework.TestCase;

/**
 * @author dmzhg
 */
public class ConfigurationTest extends TestCase {

	private Configuration config;
	private WAManager app;

	@Override
	protected void setUp() {
		app = new WAManager();
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

