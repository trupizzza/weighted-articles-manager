package com.onegolabs.wamanager.scales.aclas;

import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.scales.ScalesLoader;
import com.onegolabs.wamanager.scales.ScalesLoaderFactory;

public class AclasLoaderFactory implements ScalesLoaderFactory {

    public ScalesLoader createLoader(Configuration config) {
        return new AclasLoader(config);
    }
}
