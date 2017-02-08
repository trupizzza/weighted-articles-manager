package com.onegolabs.test.suites;

import com.onegolabs.test.ConfigurationTest;
import com.onegolabs.test.ConnectionFactoryTest;
import com.onegolabs.test.InitializationExceptionTest;
import com.onegolabs.test.MessagesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({ConfigurationTest.class, MessagesTest.class, ConnectionFactoryTest.class, InitializationExceptionTest.class})

/**
 * @author dmzhg
 */ public class CoreTestSuite {
}
