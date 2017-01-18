package com.onegolabs.scales.aclas;

import com.onegolabs.scales.ScalesCommandException;
import com.onegolabs.scales.ScalesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author dmzhg
 */
public class AclasLoader implements ScalesLoader {

    static {
        System.loadLibrary("AclasLoader");
    }

    Logger LOGGER = LoggerFactory.getLogger(AclasLoader.class);

    private Properties config;
    private int defaultUnit;
    private int defaultUnitPiece;
    private int defaultBarcode;
    private int maxNameLength;

    // The following fields are managed by the native DLL
    private long addr;
    private long hresult;
    private int errno;
    private String errmsg;

    public AclasLoader(Properties config) {
        this.config = config;
        this.defaultUnit = Integer.parseInt(config.getProperty("unit"));
        this.defaultUnitPiece = Integer.parseInt(config.getProperty("unitPiece"));
        this.defaultBarcode = Integer.parseInt(config.getProperty("barcode"));
        this.maxNameLength = 248;
        try {
            maxNameLength = Integer.parseInt(config.getProperty("maxNameLength"));
        } catch (Exception e) {
            LOGGER.error("Configuration error: failed to get property. " + e.getMessage(), e);
        }

        this.addr = 0L;
        this.hresult = 0L;
        this.errno = 0;
    }

    public int getMessageColumns() {
        return 0;
    }

    public int getMessageRows() {
        return 0;
    }

    public void connect() throws ScalesCommandException {

    }

    public void clearAll() throws ScalesCommandException {

    }

    public void beginUpload() throws ScalesCommandException {

    }

    public int uploadMessage(String[] lines) throws ScalesCommandException {
        return 0;
    }

    public int uploadArticle(int plu, int code, String name, double price, int life, int msg, boolean hasToBeWeighted, int label) throws ScalesCommandException {
        return 0;
    }

    public void endUpload() throws ScalesCommandException {

    }

    public void disconnect() throws ScalesCommandException {

    }

    //    private void throwScalesException() throws ScalesCommandException {
//        if (hresult != 0L) {
//            throw new ScalesCommandException("OLE error " + hresult, ScalesConst.ERROR_OLE, hresult);
//        } else if (errno != 0) {
//            throw new ScalesCommandException(errmsg, ScalesConst.ERROR_SCALES, errno);
//        } else {
//            throw new ScalesCommandException(ScalesConst.ERROR_UNKNOWN, 0L);
//        }
//    }
//
//    public int getMessageColumns() {
//        int cols = 246;
//        try {
//            cols = Integer.parseInt(config.getProperty("labelCols"));
//        } catch (Exception e) {
//        }
//        return cols;
//    }
//
//    public int getMessageRows() {
//        int rows = 16;
//        try {
//            rows = Integer.parseInt(config.getProperty("labelRows"));
//        } catch (Exception e) {
//        }
//        return rows;
//    }
//
//    private native boolean connectToScales(String address);
//
//    public void connect() throws ScalesCommandException {
//        if (!connectToScales(config.getProperty("address")) || addr == 0L) {
//            throwScalesException();
//        }
//    }
//
//    public void beginUpload() throws ScalesCommandException {
//        // This method has nothing to do
//    }
//
//    private native int addMessage();
//
//    private native boolean setMessageLine(int line, String text);
//
//    public int uploadMessage(String[] lines) throws ScalesCommandException {
//        int messageNo = -1;
//        if (lines.length > 0) {
//            messageNo = addMessage();
//            if (messageNo < 1) {
//                throwScalesException();
//            }
//
//            int i = 1;
//            for (String line : lines) {
//                if (!setMessageLine(i++, line)) {
//                    throwScalesException();
//                }
//            }
//        }
//        return messageNo;
//    }
//
//    private native int sendArticleToScales(int plu, int code, String name, double price, int life, int unit,
//                                           int barcode, int message, int label);
//
//    public int uploadArticle(int plu, int code, String name, double price, int life, int message,
//                             boolean htbw, int label) throws ScalesCommandException {
//
//        if (maxNameLength > 0 && name.length() > maxNameLength) {
//            name = name.substring(0, maxNameLength - 1);
//        }
//
//        int articleNo =
//                sendArticleToScales(plu, code, name, price, life, (htbw ? defaultUnit : defaultUnitPiece),
//                        defaultBarcode, message, label);
//
//        if (articleNo < 1) {
//            // throwScalesException();
//            throw new ScalesCommandException("Could not upload article: "
//                    + "plu=" + plu + ", code=" + code + ", name=" + name + ", price="
//                    + price + ", life=" + life + ", label = " + label + ", htbw="
//                    + htbw + ", errno=" + errno + ", errmsg=" + errmsg);
//        }
//
//        return articleNo;
//    }
//
//    private native boolean flushBuffers();
//
//    public void endUpload() throws ScalesCommandException {
//        if (!flushBuffers()) {
//            throwScalesException();
//        }
//    }
//
//    private native boolean clearAllPLUAndMessages();
//
//    public void clearAll() throws ScalesCommandException {
//        if (!clearAllPLUAndMessages()) {
//            throwScalesException();
//        }
//    }
//
//    private native boolean disconnectFromScales();
//
//    public void disconnect() throws ScalesCommandException {
////        if (!disconnectFromScales()) {
////            throwScalesException();
////        }
////        addr = 0L;
//    }
}
