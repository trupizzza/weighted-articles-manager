package com.onegolabs.wamanager.context;

import com.onegolabs.resources.Messages;
import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.scales.ScalesLoader;
import com.onegolabs.wamanager.scales.ScalesLoaderFactory;
import com.onegolabs.wamanager.scales.aclas.AclasLoaderFactory;

/**
 * @author dmzhg
 */
public class Context {

    private Configuration configuration;
    private String shopName;
    private String shopDescription;

    public boolean isSilentMode() {
        return silentMode;
    }

    public void setSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
    }

    private boolean silentMode;
    private static Context context = new Context();

    public ScalesLoader getScalesLoader() {
        ScalesLoaderFactory factory = new AclasLoaderFactory();
        return factory.createLoader(getConfiguration());
    }

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

    public static Context getContext() {
        return context;
    }

    public String getVersion() {
        String version = getClass().getPackage().getImplementationVersion();
        if (version == null) {
            version = Messages.getString("application.version.default");
        }
        return version;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }
}
