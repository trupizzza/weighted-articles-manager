import com.onegolabs.Messages;
import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.exception.InitializationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author dmzhg
 */
public class InitializationExceptionTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private Configuration config = new Configuration();

	@Test
	public void testExceptionHappening() throws InitializationException {
		thrown.expect(InitializationException.class);
		throw new InitializationException();
	}

	@Test
	public void testConfigFileNotFoundInitializationException() throws InitializationException {
		thrown.expect(InitializationException.class);
		thrown.expectMessage(Messages.getString("configFileNotFound"));
		config.init();
	}

	@Test
	public void testWrapping() throws InitializationException {
		thrown.expect(InitializationException.class);
		throw new InitializationException("LOL-KEK_ChEBUREK", new IllegalArgumentException(), null);
	}
}
