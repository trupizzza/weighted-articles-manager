package com.onegolabs.resources;

import com.onegolabs.exception.SystemCode;
import com.onegolabs.exception.SystemException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dmzhg
 */
public class Queries {
    public static String getQuery(String name) {
        return loadResource(name);
    }

    private static String loadResource(String name) {
        if (name == null) {
            throw new SystemException(SystemCode.QUERY_NAME_CANT_BE_NULL);
        }
        // gets folder "${PROJECT}/com.onegolabs.test.queries/"
        final File queriesDir = new File("queries");
        if (queriesDir.listFiles() == null) {
            throw new MissingResourceException(
                    "No com.onegolabs.test.queries found in given folder",
                    Queries.class.toString(),
                    queriesDir.getAbsolutePath());
        }

        List<File> result = Stream.of(queriesDir.listFiles()).filter(File::isFile).collect(Collectors.toList());
        FileInputStream input;
        try {
            input = new FileInputStream(result.stream()
                                              .filter(file -> file.getName().equals(name + ".sql"))
                                              .findAny()
                                              .orElseThrow(FileNotFoundException::new));
        } catch (FileNotFoundException e) {
            throw SystemException.wrap(e, SystemCode.QUERY_FILE_NOT_FOUND);
        }
        return readFromFileStream(input);
    }

    private static String readFromFileStream(FileInputStream input) {
        int read;
        StringBuilder result = new StringBuilder();
        try {
            while ((read = input.read()) != -1) {
                result.append((char) read);
            }
        } catch (IOException e) {
            throw SystemException.wrap(e, SystemCode.ERROR_WHILE_READING_FILE);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                throw SystemException.wrap(ex, SystemCode.ERROR_WHILE_CLOSING_FILE_INPUT_STREAM);
            }
        }
        return result.toString();
    }


}
