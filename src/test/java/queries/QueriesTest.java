package queries;

import com.onegolabs.resources.Queries;
import com.onegolabs.wamanager.exception.SystemException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

/**
 * @author dmzhg
 */
public class QueriesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getQuery() {
        assertNotNull(Queries.getQuery("main"));
    }

    @Test
    public void shouldThrowExceptionIfNameNotProvided() {
        thrown.expect(SystemException.class);
        Queries.getQuery(null);
    }

    @Test
    public void shouldThrowExceptionIfNoQueryFound() {
        thrown.expect(SystemException.class);
        Queries.getQuery("notExists");
    }
}
