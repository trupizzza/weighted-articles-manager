package com.onegolabs;

import javafx.concurrent.Task;

/**
 * @author dmzhg
 */
public class SimpleTask extends Task<Void> {

	@Override
	protected Void call() throws Exception {
		int max = 50;
		for (int i = 1; i <= max; i++) {
			if (isCancelled()) {
				break;
			}

			updateProgress(i, max);
			updateMessage("Now going i = " + i);
			Thread.sleep(100);
		}
		return null;
	}
}
