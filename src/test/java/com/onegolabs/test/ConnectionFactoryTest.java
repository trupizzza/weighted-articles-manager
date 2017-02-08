package com.onegolabs.test;

import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.dbconnection.ConnectionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

/**
 * @author dmzhg
 */
public class ConnectionFactoryTest {
    private static Configuration config;

    @BeforeClass
    public static void setUp() {
        config = new Configuration();
        config.init();
    }

    @Test
    public void testConnect() throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        assertNotNull(conn);
    }
}
