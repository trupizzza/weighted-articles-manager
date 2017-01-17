package com.onegolabs.scales;

import java.util.Properties;

/**
 * @author dmzhg
 */
public interface ScalesLoaderFactory {

    ScalesLoader createLoader(Properties config);
}
