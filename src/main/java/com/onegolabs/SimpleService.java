package com.onegolabs;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * @author dmzhg
 */
public class SimpleService extends Service<Void> {
	@Override
	protected Task<Void> createTask() {
		return new SimpleTask();
	}
}
