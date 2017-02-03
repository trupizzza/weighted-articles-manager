package com.onegolabs.exception;

import com.onegolabs.resources.Messages;
import com.onegolabs.wamanager.exception.ErrorCode;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author dmzhg
 */
public abstract class BaseException extends RuntimeException {

    Map<String, Object> properties = new TreeMap<>();
    ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public void showErrorMessage(Exception ex) {
        String title = Messages.getString("errorLoadConfigTitle");
        String message = Messages.getString("errorLoadConfigMessage");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText(title);
        alert.setContentText(message);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        String exceptionLabelError = Messages.getString("exceptionLabelError");
        Label label = new Label(exceptionLabelError);

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);
        alert.showAndWait();
        Platform.exit();
    }

    public void printStackTrace(PrintWriter s) {
        synchronized (s) {
            s.println(this);
            if (errorCode != null) {
                s.println("\t-------------------------------");
                s.println("\t" + errorCode + ":" + errorCode.getClass().getName());
            }
            for (String key : properties.keySet()) {
                s.println("\t" + key + "=[" + properties.get(key) + "]");
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (StackTraceElement aTrace : trace) s.println("\tat " + aTrace);
            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }

    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public BaseException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) properties.get(name);
    }
}
