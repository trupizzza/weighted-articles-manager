package com.onegolabs.wamanager.preloaders;

import javafx.application.Preloader;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author dmzhg
 */
public class WamInitPreLoader extends Preloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(WamInitPreLoader.class);

    private static String getHelpMessage() {
        return "\nProper usage:" +
                "[-su]" +
                "\nParameters:\n" +
                "-su					Runs Swae in silent upload mode.\n";
    }

    @Override
    public void start(Stage stage) {
        checkWrongArgs();
    }

    private void checkWrongArgs() {
        if (!getParameters().getRaw().isEmpty()) {
            Optional<String> result = getParameters().getRaw().stream().filter(parameter -> !parameter.equals("-su"))
                                                     .findFirst();
            if (!result.equals(Optional.empty())) {
                throw new IllegalArgumentException("Wrong argument: " + result + getHelpMessage());
            }
        }
    }
}
