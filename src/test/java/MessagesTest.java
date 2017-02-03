import com.onegolabs.resources.Messages;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dmzhg
 */
public class MessagesTest {

	@Test
	public void shouldGetMessage() {
		assertEquals(Messages.getString("test"), "\u0442\u0435\u0441\u0442");
	}
}
