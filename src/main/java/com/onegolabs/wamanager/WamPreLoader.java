package com.onegolabs.wamanager;

import com.onegolabs.wamanager.dbconnection.ConnectionManager;
import com.onegolabs.wamanager.exception.InitializationException;
import javafx.application.Preloader;
import javafx.stage.Stage;

/**
 * @author dmzhg
 */
public class WamPreLoader extends Preloader {

	private static Configuration settings;

	public static Configuration getConfiguration() {
		return settings;
	}

	@Override
	public void start(Stage stage) {
		try {
			settings = new Configuration();
			settings.init();
			ConnectionManager connectionManager = new ConnectionManager();
		} catch (InitializationException initEx) {

		}
	}

//	private static void checkArgs(String[] args) {
//		System.out.println(System.currentTimeMillis());
//		List<String> arguments = Arrays.asList(args);
//		List<String> checkedArgs = new ArrayList<String>();
//		boolean hasSilent = false;
//		boolean hasUpload = false;
//		for (String arg : arguments) {
//			if (!arg.startsWith("-")) {
//				LOGGER.error("Wrong argument: '" + arg + "'. Argument must start with '-'");
//				LOGGER.info(getHelpMessage());
//				System.exit(0);
//			}
//			if (!KNOWN_ARGUMENTS.contains(arg)) {
//				LOGGER.error("Unknown argument: '" + arg + "'");
//				LOGGER.info(getHelpMessage());
//				System.exit(0);
//			}
//			if (arg.equalsIgnoreCase("-s") || arg.equalsIgnoreCase("-silent")) {
//				hasSilent = true;
//			}
//			if (arg.equalsIgnoreCase("-u") || arg.equalsIgnoreCase("-upload")) {
//				hasUpload = true;
//			}
//		}
//		if (hasSilent && !hasUpload || !hasSilent && hasUpload) {
//			LOGGER.error("Cannot use upload and silent mode parameters separately!");
//			LOGGER.info(getHelpMessage());
//			System.exit(0);
//		}
//		if (hasSilent) {
//			setSilentMode(true);
//			LOGGER.info("Swae is now running in silent mode!");
//		}
//		System.out.println(System.currentTimeMillis());
//	}

	private static String getHelpMessage() {
		return "\n\nProper usage:" +
				"[-s][-silent][-u][-upload]" +
				"\nParameters:\n" +
				"-s or -silent					Runs Swae in silent mode. You can't use this parameter alone\n" +
				"-u or -upload					Execute Upload task. Work only with -s or -silent";
	}
}
