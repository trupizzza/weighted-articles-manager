package com.onegolabs.scales;

/**
 * @author dmzhg
 */
public interface ScalesLoader {

    int getMessageColumns();

    int getMessageRows();

    void connect() throws ScalesCommandException;

    void clearAll() throws ScalesCommandException;

    void beginUpload() throws ScalesCommandException;

    int uploadMessage(String[] lines) throws ScalesCommandException;

    int uploadArticle(int plu, int code, String name, double price,
                      int life, int msg, boolean hasToBeWeighted, int label)
            throws ScalesCommandException;

    void endUpload() throws ScalesCommandException;

    void disconnect() throws ScalesCommandException;
}