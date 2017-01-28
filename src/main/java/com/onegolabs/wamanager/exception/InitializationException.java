package com.onegolabs.wamanager.exception;

import com.onegolabs.Messages;
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
 * Class for handling exceptions during app initialize process
 * Handles configuration/context exceptions
 *
 * @author dmzhg
 */
public class InitializationException extends RuntimeException {

	private final Map<String, Object> properties = new TreeMap<>();
	private ErrorCode errorCode;

	public InitializationException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public InitializationException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public InitializationException(Throwable cause, ErrorCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public InitializationException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public InitializationException() {
		super();
	}

	public static InitializationException wrap(Throwable exception, ErrorCode errorCode) {
		if (exception instanceof InitializationException) {
			InitializationException se = (InitializationException) exception;
			if (errorCode != null && errorCode != se.getErrorCode()) {
				return new InitializationException(exception.getMessage(), exception, errorCode);
			}
			return se;
		} else {
			return new InitializationException(exception.getMessage(), exception, errorCode);
		}
	}

	public static InitializationException wrap(Throwable exception) {
		return wrap(exception, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) properties.get(name);
	}

	public InitializationException set(String name, Object value) {
		properties.put(name, value);
		return this;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			printStackTrace(new PrintWriter(s));
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		synchronized (s) {
			s.println(this);
			s.println("\t-------------------------------");
			if (errorCode != null) {
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

	private void showErrorMessage(Exception ex) {
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
		System.exit(1);
	}
}
