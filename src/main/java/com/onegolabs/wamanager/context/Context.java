package com.onegolabs.wamanager.context;

import com.onegolabs.wamanager.Configuration;

/**
 * @author dmzhg
 */
public class Context {
    private Configuration configuration;
    private String shopName;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
