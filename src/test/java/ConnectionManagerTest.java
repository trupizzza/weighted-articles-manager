import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.dbconnection.ConnectionManager;
import junit.framework.TestCase;

/**
 * @author dmzhg
 */
public class ConnectionManagerTest extends TestCase {
	private Configuration config = new Configuration();

	@Override
	protected void setUp() {
		config = new Configuration();
	}

	public void testConnect() throws Exception {
		config.init();
		ConnectionManager connMgr = new ConnectionManager();
		connMgr.connect(config);
	}
}
