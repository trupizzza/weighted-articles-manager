package com.onegolabs.wamanager.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * @author dmzhg
 */
public class UploadToScalesService extends Service<Void> {

    @Override
    protected Task<Void> createTask() {
        return new UploadToScalesTask();
    }
}
