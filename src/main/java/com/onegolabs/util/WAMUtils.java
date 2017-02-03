package com.onegolabs.util;

import com.onegolabs.wamanager.scales.ScalesLoader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.BreakIterator;
import java.util.ArrayList;

/**
 * @author dmzhg
 */
public class WAMUtils {

    private static final int SIZE_OF_INT_IN_HALF_BYTES = 8;
    private static final int NUMBER_OF_BITS_IN_A_HALF_BYTE = 4;
    private static final int HALF_BYTE = 0x0F;
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Transforms hostname string to IP-address string
     *
     * @param alias hostname
     * @return string representation of IP-address via given hostname
     * @throws UnknownHostException if address is not recognized by given hostname
     */
    public static String getIPAddressByAlias(String alias) throws UnknownHostException {
        return InetAddress.getByName(alias).getHostAddress();
    }

    /**
     * Converts given integer number to hexadecimal string
     *
     * @param dec number to covert
     * @return string representing hex code of given number
     */
    public static String decToHex(int dec) {
        StringBuilder hexBuilder = new StringBuilder(SIZE_OF_INT_IN_HALF_BYTES);
        hexBuilder.setLength(SIZE_OF_INT_IN_HALF_BYTES);
        for (int i = SIZE_OF_INT_IN_HALF_BYTES - 1; i >= 0; --i) {
            int j = dec & HALF_BYTE;
            hexBuilder.setCharAt(i, HEX_DIGITS[j]);
            dec >>= NUMBER_OF_BITS_IN_A_HALF_BYTE;
        }
        return hexBuilder.toString();
    }

    /**
     * Cut strings
     * TODO: analyze what this method doing
     *
     * @param txt    incoming text string
     * @param loader current Scales loader class
     * @return wrapped text
     */
    public static String[] getWrappedText(String txt, ScalesLoader loader) {
        int rows = loader.getMessageRows();
        int cols = loader.getMessageColumns();

        if (txt == null || txt.isEmpty() || rows < 1 || cols < 1) {
            return null;
        }

        ArrayList<String> lines = new ArrayList<String>();
        BreakIterator boundary = BreakIterator.getLineInstance();
        boundary.setText(txt);

        String curr = "", part = "";
        int start = boundary.first(), end, ln = 0;
        boolean doBreak = false, done = false;

        do {
            if (part.isEmpty()) {
                if ((end = boundary.next()) == BreakIterator.DONE) {
                    doBreak = !curr.isEmpty();
                    done = true;
                } else {
                    part = txt.substring(start, end);
                    start = end;
                }
            }

            if (!part.isEmpty()) {
                if (curr.length() + part.length() > cols) {
                    if (curr.isEmpty()) {
                        curr = part.substring(0, cols);
                        part = part.substring(cols);
                    }
                    doBreak = true;
                } else {
                    if (part.endsWith("\n")) {
                        part = part.substring(0, part.length() - 1);
                        doBreak = true;
                    }
                    curr += part;
                    part = "";
                }
            }

            if (doBreak) {
                lines.add(curr);
                curr = "";
                doBreak = false;

                if (++ln >= rows) {
                    done = true;
                }
            }
        } while (!done);

        String[] linesArr = new String[lines.size()];
        return lines.toArray(linesArr);
    }
}
