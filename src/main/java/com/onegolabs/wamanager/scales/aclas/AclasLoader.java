package com.onegolabs.wamanager.scales.aclas;

import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.exception.ScalesCommandCode;
import com.onegolabs.wamanager.exception.scales.ScalesCommandException;
import com.onegolabs.wamanager.scales.ScalesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dmzhg
 */
public class AclasLoader implements ScalesLoader {

    static {
        System.loadLibrary("AclasLoader");
    }

    Logger LOGGER = LoggerFactory.getLogger(AclasLoader.class);

    private Configuration config;
    private int defaultUnit;
    private int defaultUnitPiece;
    private int defaultBarcode;
    private int maxNameLength;

    // The following fields are managed by the native DLL
    private long address;
    private long result;
    private int errorNumber;
    private String errorMessage;

    public AclasLoader(Configuration config) {
        this.config = config;
        this.defaultUnit = Integer.parseInt(config.getScalesProperty("unit"));
        this.defaultUnitPiece = Integer.parseInt(config.getScalesProperty("unitPiece"));
        this.defaultBarcode = Integer.parseInt(config.getScalesProperty("barcode"));
        this.maxNameLength = 248;
        try {
            maxNameLength = Integer.parseInt(config.getScalesProperty("maxNameLength"));
        } catch (Exception e) {
            LOGGER.error("Configuration error: failed to get property. " + e.getMessage(), e);
        }
        this.address = 0L;
        this.result = 0L;
        this.errorNumber = 0;
    }

    public int getMessageColumns() {
        return 0;
    }

    public int getMessageRows() {
        return 0;
    }

    public void connect() throws ScalesCommandException {
        LOGGER.debug("Trying to connect to scales...");
        if (!connectToScales(config.getScalesProperty("address")) || address == 0L) {
            throw new ScalesCommandException(ScalesCommandCode.NO_CONNECTION_VIA_GIVEN_ADDRESS);
        }
        LOGGER.debug("Connect successful!");
    }

    public void clearAll() throws ScalesCommandException {
    }

    public void beginUpload() throws ScalesCommandException {
    }

    public int uploadMessage(String[] lines) throws ScalesCommandException {
        return 0;
    }

    public int uploadArticle(
            int plu,
            int code,
            String name,
            double price,
            int life,
            int msg,
            boolean hasToBeWeighted,
            int label) throws ScalesCommandException {
        return 0;
    }

    public void endUpload() throws ScalesCommandException {
    }

    public void disconnect() throws ScalesCommandException {
    }

    private native boolean connectToScales(String address);

    private native int addMessage();

    private native boolean setMessageLine(int line, String text);

    private native int sendArticleToScales(
            int plu, int code, String name, double price, int life, int unit, int barcode, int message, int label);

    private native boolean flushBuffers();

    private native boolean clearAllPLUAndMessages();

    private native boolean disconnectFromScales();
}
