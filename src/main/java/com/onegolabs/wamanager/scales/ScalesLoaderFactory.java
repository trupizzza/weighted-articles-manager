package com.onegolabs.wamanager.scales;

import com.onegolabs.wamanager.Configuration;

/**
 * @author dmzhg
 */
public interface ScalesLoaderFactory {

    ScalesLoader createLoader(Configuration config);
}
