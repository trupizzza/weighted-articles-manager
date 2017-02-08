package com.onegolabs.test.suites;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @author dmzhg
 */
public class MainRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CoreTestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
