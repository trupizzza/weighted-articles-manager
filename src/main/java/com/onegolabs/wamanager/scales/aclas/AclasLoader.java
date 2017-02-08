package com.onegolabs.wamanager.scales.aclas;

import com.onegolabs.exception.BaseException;
import com.onegolabs.resources.Messages;
import com.onegolabs.util.WAMUtils;
import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.exception.scales.ScalesCommandCode;
import com.onegolabs.wamanager.exception.scales.ScalesCommandException;
import com.onegolabs.wamanager.scales.ScalesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

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
        int cols = 246;
        try {
            cols = Integer.parseInt(config.getScalesProperty("labelCols"));
        } catch (NumberFormatException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return cols;
    }

    public int getMessageRows() {
        int rows = 16;
        try {
            rows = Integer.parseInt(config.getScalesProperty("labelRows"));
        } catch (NumberFormatException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return rows;
    }

    public void connect() throws ScalesCommandException {
        String addressProperty = config.getScalesProperty("address");
        if (addressProperty == null || addressProperty.isEmpty()) {
            throw new ScalesCommandException(ScalesCommandCode.IP_ADDRESS_NOT_DEFINED);
        }
        String ipAddress;
        try {
            ipAddress = WAMUtils.getIPAddressByAlias(addressProperty);
        } catch (UnknownHostException e) {
            throw ScalesCommandException.wrap(e, ScalesCommandCode.COULDNT_CONNECT_TO_ADDRESS);
        }
        LOGGER.debug("Trying to connect to scales...");
        if (!connectToScales(ipAddress) || address == 0L) {
            throw new ScalesCommandException(ScalesCommandCode.NO_CONNECTION_VIA_GIVEN_ADDRESS).set("IP-address",
                    ipAddress);
        }
        LOGGER.debug("Connect successful!");
    }

    public void clearAll() throws ScalesCommandException {
        LOGGER.debug("Trying to clear PLU and messages");
        if (!clearAllPLUAndMessages()) {
            throw new ScalesCommandException(ScalesCommandCode.FAILED_TO_CLEAR_PLU_AND_MSG);
        }
        LOGGER.debug("Clearing PLU and messages completed successfully");
    }

    public void beginUpload() throws ScalesCommandException {
        // This method has nothing to do
    }

    public int uploadMessage(String[] lines) throws ScalesCommandException {
        int messageNo = -1;
        if (lines.length > 0) {
            messageNo = addMessage();
            if (messageNo < 1) {
                throwScalesCommandException();
            }
            int i = 1;
            for (String line : lines) {
                if (setMessageLine(i++, line)) {
                    LOGGER.debug("uploadMessage is done successfully! Line: " + line);
                } else {
                    throwScalesCommandException();
                }
            }
        }
        return messageNo;
    }

    @Override
    public int uploadArticle(
            int plu,
            int code,
            String name,
            double price,
            int life, int message,
            boolean hasToBeWeighted, int label, int discount) throws ScalesCommandException {
        if (maxNameLength > 0 && name.length() > maxNameLength) {
            name = name.substring(0, maxNameLength - 1);
        }
        LOGGER.debug("Trying to upload article: " + "plu=" + plu + ", code=" + code + ", name='" + name + "', price=" + price + ", life=" + life + ", label = " + label + ", htbw=" + hasToBeWeighted + ", defaultBarcode=" + defaultBarcode + ", message=" + message);
        LOGGER.debug("defaultUnit = " + defaultUnit);
        LOGGER.debug("defaultUnitPiece = " + defaultUnitPiece);
        int articleNo = sendArticleToScales(plu,
                code,
                name,
                price,
                life,
                (hasToBeWeighted ? defaultUnit : defaultUnitPiece),
                defaultBarcode,
                message,
                label,
                discount);

        LOGGER.debug("articleNo = " + articleNo);

        if (articleNo < 1) {
            throw new ScalesCommandException(ScalesCommandCode.COULDNT_UPLOAD_ARTICLE).set("PLU", plu)
                                                                                      .set("code", code)
                                                                                      .set("name", name)
                                                                                      .set("price", price)
                                                                                      .set("life", life)
                                                                                      .set("label", label)
                                                                                      .set(
                                                                                              "hasToBeWeighted",
                                                                                              hasToBeWeighted)
                                                                                      .set("errorNumber", errorNumber)
                                                                                      .set(
                                                                                              "errorMessage",
                                                                                              errorMessage);
        }
        LOGGER.debug("Article " + code + " uploaded successfully");
        return articleNo;
    }

    private void throwScalesCommandException() throws ScalesCommandException {
        BaseException e = new ScalesCommandException();
        if (result != 0L) {
            e = e.set("result", Messages.getExceptionText("win.error." + WAMUtils.decToHex((int) result)))
                 .set("OLE ERROR", result);
        } else if (errorNumber != 0) {
            e = e.set("errorMessage", errorMessage).set("errorNumber", errorNumber);
        } else {
            throw new ScalesCommandException(ScalesCommandCode.UNKNOWN);
        }
        throw e;
    }

    public void endUpload() throws ScalesCommandException {
        if (flushBuffers()) {
            LOGGER.debug("Done endUpload (native flushBuffers) successfully");
        } else {
            throwScalesCommandException();
        }
    }

    public void disconnect() throws ScalesCommandException {
        if (disconnectFromScales()) {
            LOGGER.debug("disconnect (native disconnectFromScales) is done successfully!");
        } else {
            throwScalesCommandException();
        }
        address = 0L;
    }

    private native boolean connectToScales(String address);

    private native int addMessage();

    private native boolean setMessageLine(int line, String text);

    private native int sendArticleToScales(
            int plu,
            int code,
            String name,
            double price,
            int life,
            int unit,
            int barcode,
            int message,
            int label,
            int discount);

    private native boolean flushBuffers();

    private native boolean clearAllPLUAndMessages();

    private native boolean disconnectFromScales();
}
