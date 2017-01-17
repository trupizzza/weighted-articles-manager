package com.onegolabs.scales.aclas;

import com.onegolabs.scales.ScalesLoader;
import com.onegolabs.scales.ScalesLoaderFactory;

import java.util.Properties;

public class AclasLoaderFactory implements ScalesLoaderFactory {

    public ScalesLoader createLoader(Properties config) {
        return new AclasLoader(config);
    }
}
